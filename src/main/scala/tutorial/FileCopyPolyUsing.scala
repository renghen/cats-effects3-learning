package tutorial

import cats.effect.{IO, Resource, IOApp, ExitCode, Sync, Async}
import cats.effect.std.Semaphore
import cats.syntax.all.*

import java.io.*

object FileCopyPolyUsing extends IOApp{

  def inputStream[F[_]](f: File, guard: Semaphore[F])(using sync: Sync[F]): Resource[F, FileInputStream] =
    Resource.make {
      sync.delay(FileInputStream(f))
    } { inStream =>
      guard.permit.use { _ =>
        sync.delay(inStream.close()).handleErrorWith(_ => sync.unit)
      }
    }

  def outputStream[F[_]](f: File, guard: Semaphore[F])(using sync: Sync[F]): Resource[F, FileOutputStream] =
    Resource.make {
      sync.delay(new FileOutputStream(f))
    } { outStream =>
      guard.permit.use { _ => sync.delay(outStream.close()).handleErrorWith(_ => sync.unit)
      }
    }

  def inputOutputStreams[F[_]](in: File, out: File,
    guard: Semaphore[F])(using sync: Sync[F]): Resource[F, (InputStream, OutputStream)] =
    for {
      inStream <- inputStream(in, guard)
      outStream <- outputStream(out, guard)
    } yield (inStream, outStream)

  def transmit[F[_]](
      origin: InputStream,
      destination: OutputStream,
      buffer: Array[Byte],
      acc: Long
  )(using sync: Sync[F]): F[Long] =
    for {
      amount <- sync.blocking(origin.read(buffer, 0, buffer.length))
      count <-
        if (amount > -1)
          sync.blocking(destination.write(buffer, 0, amount)) >> transmit(
            origin,
            destination,
            buffer,
            acc + amount
          )
        else
          sync.pure(
            acc
          ) // End of read stream reached (by java.io.InputStream contract), nothing to write
    } yield count // Returns the actual amount of bytes transmitted

  def transfer[F[_]](
      origin: InputStream,
      destination: OutputStream
  )(using sync: Sync[F]): F[Long] =
    for {
      buffer <- sync.delay(
        new Array[Byte](1024 * 10)
      ) // Allocated only when the IO is evaluated
      total <- transmit(origin, destination, buffer, 0L)
    } yield total

  def copy[F[_]](origin: File, destination: File)(using async: Async[F]): F[Long] =
    for {
      guard <- Semaphore[F](1)
      count <- inputOutputStreams(origin, destination, guard).use {
        case (in, out) =>
          guard.permit.use { _ =>
            transfer(in, out)
          }
      }
    } yield count

  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <-
        if (args.length < 2)
          IO.raiseError(
            new IllegalArgumentException("Need origin and destination files")
          )
        else IO.unit
      orig = new File(args(0))
      dest = new File(args(1))
      count <- copy[IO](orig, dest)
      _ <- IO.println(
        s"$count bytes copied from ${orig.getPath} to ${dest.getPath}"
      )
    } yield ExitCode.Success

  }