package tutorial

/** Implement the missing parts.
  */
object Main extends App:
  val msg = Vector(0x4a, 0x6f, 0x69, 0x6e, 0x20, 0x2605, 0x6d, 0x69, 0x6e, 0x64)

  def convert[T](seq: Seq[T], f: T => Char): String = 
    seq.foldLeft("")((str, el) => str + f(el))

  println(convert[Int](msg, { el => el.toChar }))
