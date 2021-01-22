package ce101

import cats.effect._
import cats.syntax.all._
import cats.effect.std.Random

import scala.concurrent.duration._

object Hello extends IOApp.Simple {
  val rnd = Random.scalaUtilRandom[IO]

  def greeter(word: String): IO[Unit] = rnd.>>= { r =>
    r.nextIntBounded(5) >>= { d =>
      (IO.println(word) >> IO.sleep((d * 10).millis)).foreverM
    }
  }

  override def run =
    for {
      hello <- greeter("Hello").start
      world <- greeter("World").start
      _ <- IO.sleep(5.seconds)
      _ <- hello.cancel >> world.cancel
    } yield ()
}
