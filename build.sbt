val scala3Version = "3.0.0-M3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-simple",
    version := "0.1.0",
    scalaVersion := scala3Version,
    addCompilerPlugin("com.olegpy" % "better-monadic-for_2.13" % "0.3.1"),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.3.1",
      "org.typelevel" %% "cats-effect" % "3.0.0-M5",
      "co.fs2" %% "fs2-io" % "3.0.0-M7",
      "org.scalameta" %% "munit" % "0.7.21" % Test
    )
  )
