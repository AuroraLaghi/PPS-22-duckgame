ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.1"

lazy val osNames = Seq("linux", "mac", "win")

lazy val root = (project in file("."))
  .settings(
    name := "duckgame",
    idePackagePrefix := Some("it.unibo.pps.duckgame"),
    assembly / assemblyJarName := "duckgame.jar",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.11" % Test,
      "org.scalatestplus" %% "selenium-4-1" % "3.2.11.0" % Test,
      "org.scalatestplus" %% "scalacheck-1-15" % "3.2.10.0" % Test,
      "org.scalatestplus" %% "mockito-3-12" % "3.2.10.0" % Test,
      "com.lihaoyi" %% "requests" % "0.6.9",
      "org.json4s" %% "json4s-jackson" % "4.0.3",
      "org.scalafx" %% "scalafx" % "16.0.0-R24",
      "it.unibo.alice.tuprolog" % "2p-core" % "4.1.1",
      "it.unibo.alice.tuprolog" % "2p-ui" % "4.1.1",
      "org.controlsfx" % "controlsfx" % "11.1.1",
      "org.testfx" % "testfx-core" % "4.0.16-alpha" % Test,
      "org.testfx" % "testfx-junit5" % "4.0.16-alpha" % Test,
      "org.testfx" % "openjfx-monocle" % "jdk-12.0.1+2" % Test,
      "io.monix" %% "monix" % "3.4.0",
      "com.github.nscala-time" %% "nscala-time" % "2.30.0",
      "org.scalactic" %% "scalactic" % "3.2.13"
    ) ++ osNames.flatMap(os =>
      Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "16" classifier os) ),
    crossPaths := false, // https://github.com/sbt/junit-interface/issues/35
    Test / parallelExecution := false
  )

wartremoverWarnings ++= Warts.allBut(
  Wart.Any,
  Wart.Var,
  Wart.AsInstanceOf,
  Wart.Null,
  Wart.ThreadSleep,
  Wart.Nothing,
  Wart.Throw,
  Wart.ToString,
  Wart.DefaultArguments,
  Wart.AutoUnboxing,
  Wart.IsInstanceOf
)