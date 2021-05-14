// package ce101

// import fs2._
// import fs2.io.file._
// import cats.effect._

// import java.nio.file.Paths

// object Fs2RenameOP extends IOApp.Simple :
//   val dir = RenameOP.dir
//   override def run =
//     Files[IO].walk(Paths.get(dir))
//       .evalFilter(file => Files[IO].isFile(file))
//       .evalMap{ file =>
//         val newName = Paths.get(RenameOP.newName(file.getFileName.toString,dir))
//         IO{println(newName)} *> Files[IO].move(file, newName)
//       }.compile.drain
