package meta

object Debug:
  inline def hello(): Unit = println("Hello, world!")
  inline def debugSingle(expr: Any): Unit = ???

object Test extends App:
  import Debug._
  hello()  