val scala3Version = "3.0.0"
val catsEffectVersion = "3.1.1"
val catsCoreVersion = "2.6.1"
val fs2Version = "3.0.3"
val dottyCPSasync = "0.7.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-cats",
    version := "0.1.1",
    scalaVersion := scala3Version,
    Compile / run / mainClass := Some("ce101.SimpleHello"),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % catsCoreVersion,
      "org.typelevel" %% "cats-effect" % catsEffectVersion,
      "org.typelevel" %% "cats-effect-kernel" % catsEffectVersion,
      "org.typelevel" %% "cats-effect-std" % catsEffectVersion,
      "co.fs2" %% "fs2-io" % fs2Version,
      "com.github.rssh" %% "dotty-cps-async" % dottyCPSasync,
      // "com.github.rssh" %% "cps-async-connect-cats-effect" % "0.4.0",
      "org.scalameta" %% "munit" % "0.7.26" % Test
    ),
    scalacOptions ++= Seq(
      "-rewrite",
      "-indent",
      "-feature",
      "-deprecation",
      "-unchecked"
      // "-Xprint:typer"
    )
  )

// mainClass in Compile := Some("org.project.my.Main")
