package ce101

import cats.effect.*
import cats.syntax.all.*

object SimpleHello  extends IOApp.Simple:
  override def run = IO.consoleForIO.println("Hello world")