// package fs2Simple
// import fs2.Stream
// import cats.effect.{IO, IOApp}
// import cats.effect.std.Random
// import cats.syntax.all.*

// object LottoGenerator extends IOApp.Simple:
//   val rnd = Random.scalaUtilRandom[IO]
//   val rnd40 = rnd.>>= { r => r.nextIntBounded(39).map(_ + 1) }
//   val streamOfRandom : Stream[IO,Int] =  Stream.eval{rnd40}.repeat
//   //val result = streamOfRandom.scan(Set[Int]()){(s,i) => s + i}.dropThrough{re => re.size < 5 }.take(1).>>={i => Stream.emits(i.toList)}

//   // * another way *
//   val result = streamOfRandom.changes.take(6)

//   // * another way *
//   // val result = streamOfRandom
//   // .mapAccumulate(Set[Int]())((s,i) => (s + i,0))
//   // .dropThrough{re => re._1.size < 5 }
//   // .take(1)
//   // .flatMap{re => Stream.emits(re._1.toList)}

//   val run =
//   result
//   .map(_.toString)
//   .intersperse(" ")
//   .evalTap{i => IO.print(i)}
//   .compile.drain *> IO.println("")
