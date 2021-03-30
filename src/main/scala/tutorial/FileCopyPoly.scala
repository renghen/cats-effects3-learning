package tutorial

import cats.effect.{IO,Resource,IOApp,ExitCode,Sync, Async}
import cats.effect.IO.asyncForIO
import cats.effect.std.Semaphore
import java.io.*
import cats.implicits.*
import cats.syntax.all.*


object FileCopyPoly extends IOApp:
  
  def inputStream[F[_]:Sync](f: File, guard: Semaphore[F]): Resource[F, FileInputStream] =
    Resource.make {
      Sync[F].delay(FileInputStream(f))
    } { inStream => 
      guard.permit.use { _ =>
        Sync[F].delay(inStream.close()).handleErrorWith(_ => Sync[F].unit)
      }
    }

  def outputStream[F[_]: Sync](f: File, guard: Semaphore[F]): Resource[F, FileOutputStream] = 
    Resource.make {
      Sync[F].delay(new FileOutputStream(f))
    } { outStream =>
      guard.permit.use { _ =>
         Sync[F].delay(outStream.close()).handleErrorWith(_ =>  Sync[F].unit)
      }
    }
  
  def inputOutputStreams[F[_]: Sync](in: File, out: File, guard: Semaphore[F]): Resource[F, (InputStream, OutputStream)] = 
    for {
      inStream  <- inputStream(in, guard)
      outStream <- outputStream(out, guard)
    } yield (inStream, outStream)
  
  def transmit[F[_]: Sync](origin: InputStream, destination: OutputStream, buffer: Array[Byte], acc: Long): F[Long] =
    for {
      amount <- Sync[F].blocking(origin.read(buffer, 0, buffer.length))
      count  <- if(amount > -1) Sync[F].blocking(destination.write(buffer, 0, amount)) >> transmit(origin, destination, buffer, acc + amount)
                else Sync[F].pure(acc) // End of read stream reached (by java.io.InputStream contract), nothing to write
    } yield count // Returns the actual amount of bytes transmitted
  
    
  def transfer[F[_]: Sync](origin: InputStream, destination: OutputStream): F[Long] = 
    for {
      buffer <- Sync[F].delay(new Array[Byte](1024 * 10)) // Allocated only when the IO is evaluated
      total  <- transmit(origin, destination, buffer, 0L)
    } yield total
  

  def copy[F[_]: Async](origin: File, destination: File): F[Long] =
    for {
      guard <- Semaphore[F](1)
      count <- inputOutputStreams(origin, destination, guard).use { case (in, out) => 
                guard.permit.use { _ =>
                  transfer(in, out)
                }
              }
    } yield count
  
  override def run(args: List[String]): IO[ExitCode] = 
    for {
      _      <- if(args.length < 2) IO.raiseError(new IllegalArgumentException("Need origin and destination files"))
                else IO.unit
      orig = new File(args(0))
      dest = new File(args(1))
      count <- copy(orig, dest)
      _     <- IO.println(s"$count bytes copied from ${orig.getPath} to ${dest.getPath}")
    } yield ExitCode.Success