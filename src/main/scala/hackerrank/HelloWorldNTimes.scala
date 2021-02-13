package hackerrank

import cats.effect._
import cats.syntax._
import cats.implicits._

import fs2._

object HelloWorldNTimes extends cats.effect.IOApp.Simple : 

  import SolveMeFirst.readInt

  extension [A](io: IO[A])
    def repeat(n : Int) : IO[A] = 
      n.iterateUntilM(n => io.as(n-1))(_ == 1) *> io

  override def run = 
    for
      count  <- readInt("Enter number(1-50) > ",0,51)
      //_ <- Stream.repeatEval(IO.println("Hello World")).take(count).compile.drain
      // _ <- 0.iterateWhileM(n => IO.println("Hello World").as(n + 1))(_ < count).void
      // _   <- count.iterateUntilM(n => IO.println("Hello World").as(n-1))(_ == 0).void
      i <- (IO.println("Hello World") *> IO.pure(4)).repeat(count)
    yield()

