ThisBuild / version := "0.1.0"

ThisBuild / scalaVersion := "3.2.1"

lazy val osNames = Seq("linux", "mac", "win")

lazy val root = (project in file("."))
  .settings(
    name := "PPS-22-duckgame",
    assembly / assemblyJarName := "duckgame.jar",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.17" % Test,
      "org.scalafx" %% "scalafx" % "16.0.0-R24",
      "it.unibo.alice.tuprolog" % "2p-core" % "4.1.1",
      "it.unibo.alice.tuprolog" % "2p-ui" % "4.1.1",
      "org.controlsfx" % "controlsfx" % "11.1.1",
      "org.testfx" % "testfx-core" % "4.0.16-alpha" % Test,
      "org.testfx" % "testfx-junit5" % "4.0.16-alpha" % Test,
      "org.testfx" % "openjfx-monocle" % "jdk-12.0.1+2" % Test,
      "com.novocode" % "junit-interface" % "0.11" % Test, // sbt's test interface for JUnit 4
      "org.junit.jupiter" % "junit-jupiter" % "5.10.2" % Test, // aggregator of junit-jupiter-api and junit-jupiter-engine (runtime)
      "org.junit.jupiter" % "junit-jupiter-engine" % "5.10.2" % Test, // for org.junit.platform
      "org.junit.vintage" % "junit-vintage-engine" % "5.10.2" % Test,
      "org.junit.platform" % "junit-platform-launcher" % "1.10.2" % Test,
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

Compile/compile/wartremoverErrors ++=Warts.all