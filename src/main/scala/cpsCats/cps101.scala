package cpsCats

// will uncoment when cps connect comes out for cats 3.10 and scala 3-rc-3
import cps.*
// import cps.monads.cats.given
import cats.effect.{IO, SyncIO, IOApp}

object cps101 extends IOApp.Simple:

  val run = IO.consoleForIO.println("hello world")

  // val run = async {
  //   var i = 0
  //   while
  //     i += 1
  //     await (IO.print(f"$i%03d : "))
  //     if (i % 3) == 0 then await(IO.print("Fizz"))
  //     if (i % 5) == 0 then await(IO.print("Buzz"))      
  //     await(IO.println(""))
  //     i < 1000
  //   do()
  // }

