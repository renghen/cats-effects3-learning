val scala3Version = "3.0.0-RC1"
val catsEffectVersion = "3.0.0-RC2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-simple",
    version := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.4.2",
      "org.typelevel" %% "cats-effect" % catsEffectVersion,
      "org.typelevel" %% "cats-effect-kernel" % catsEffectVersion,
      "org.typelevel" %% "cats-effect-std" % catsEffectVersion,
      "co.fs2" %% "fs2-io" % "3.0-30-1163ac3",
      "org.scalameta" %% "munit" % "0.7.22" % Test
    ),
    scalacOptions ++= Seq(
      "-rewrite",
      "-indent"
    )
  )
