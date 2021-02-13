val scala3Version = "3.0.0-M3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-simple",
    version := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.4.1",
      "org.typelevel" %% "cats-effect" % "3.0.0-M5",
      "co.fs2" %% "fs2-io" % "3.0.0-M7",
      "org.scalameta" %% "munit" % "0.7.21" % Test
    )
  )
