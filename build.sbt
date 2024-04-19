ThisBuild / version := "0.1.0"

ThisBuild / scalaVersion := "3.2.1"

lazy val osNames = Seq("linux", "mac", "win")

lazy val root = (project in file("."))
  .settings(
    name := "PPS-22-duckgame",
    assembly / assemblyJarName := "duckgame.jar",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.18" % Test,
      "org.scalafx" %% "scalafx" % "16.0.0-R25",
      "it.unibo.alice.tuprolog" % "2p-core" % "4.1.1",
      "it.unibo.alice.tuprolog" % "2p-ui" % "4.1.1",
      "org.controlsfx" % "controlsfx" % "11.2.1",
      "org.scalactic" %% "scalactic" % "3.2.18",
      "org.junit.jupiter" % "junit-jupiter" % "5.10.2" % Test, // aggregator of junit-jupiter-api and junit-jupiter-engine (runtime)
      "org.junit.jupiter" % "junit-jupiter-engine" % "5.10.2" % Test, // for org.junit.platform
      "org.junit.vintage" % "junit-vintage-engine" % "5.10.2" % Test,
      "org.junit.platform" % "junit-platform-launcher" % "1.10.2" % Test,
    ) ++ osNames.flatMap(os =>
      Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "20" classifier os) ),
    crossPaths := false, // https://github.com/sbt/junit-interface/issues/35
    Test / parallelExecution := false
  )

wartremoverWarnings ++= Warts.allBut(
  Wart.Any,
  Wart.AsInstanceOf,
  Wart.Null,
  Wart.ThreadSleep,
  Wart.Nothing,
  Wart.Throw,
  Wart.ToString,
  Wart.DefaultArguments,
  Wart.AutoUnboxing,
  Wart.IsInstanceOf,
  Wart.Overloading,
  Wart.Var,
  Wart.SeqApply,
  Wart.IterableOps
)

assembly / mainClass := Some("it.unibo.pps.duckgame.Main")
assembly / assemblyOutputPath := file("target/duckgame.jar")
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}

