val scala3Version = "3.0.0-RC1"
val catsEffectVersion = "3.0.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-simple",
    version := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.5.0",
      "org.typelevel" %% "cats-effect" % catsEffectVersion,
      "org.typelevel" %% "cats-effect-kernel" % catsEffectVersion,
      "org.typelevel" %% "cats-effect-std" % catsEffectVersion,
      "co.fs2" %% "fs2-io" % "3.0.1",
      "org.scalameta" %% "munit" % "0.7.23" % Test
    ),
    scalacOptions ++= Seq(
      "-rewrite",
      "-indent",
      "-feature",
      "-deprecation",
      "-unchecked",
      "-language:postfixOps"
    )
  )
