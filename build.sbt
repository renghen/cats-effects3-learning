val scala3Version = "3.0.0-RC2"
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
      "com.github.rssh" %% "dotty-cps-async" % "0.5.0",
      "com.github.rssh" %% "cps-async-connect-cats-effect" % "0.3.0",
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
