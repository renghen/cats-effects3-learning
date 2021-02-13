package hackerrank

import cats.effect._
import scala.util.Try

import cats._
import cats.syntax._
import cats.implicits._

object SolveMeFirst extends IOApp.Simple {

  def isInRange(number : Int, start : Int = 0, end : Int = 1001) = 
    if (number > start && number < end) then
      Right(number)
    else
      Left(NumberFormatException())

  def readInt(prompt: String) : IO[Int] =
    IO.print(prompt) *>
    IO.readLine.map { str =>
      Either
        .catchOnly[NumberFormatException](str.toInt)
        .flatMap(isInRange(_))
    } >>= { result =>
      result match
        case Right(b) => IO.pure(b)
        case Left(_) => IO.println("number is not in right format") *> readInt(prompt)
  }

  override def run = 
    for
      first  <- readInt("Enter 1st number(1-1000) > ")
      second <- readInt("Enter 2sd number(1-1000) > ")
      _ <- IO.println(first + second)
    yield()
}
