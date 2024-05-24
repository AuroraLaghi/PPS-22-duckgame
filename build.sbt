ThisBuild / version := "1.0.0"

lazy val osNames = Seq("linux", "mac", "win")

lazy val root = (project in file("."))
  .settings(
    name := "PPS-22-duckgame",
    scalaVersion := "3.3.0",
    assembly / assemblyJarName := "duckgame.jar",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.18" % Test,
      "org.scalafx" %% "scalafx" % "21.0.0-R32",
      "it.unibo.alice.tuprolog" % "2p-core" % "4.1.1",
      "it.unibo.alice.tuprolog" % "2p-ui" % "4.1.1",
      "org.controlsfx" % "controlsfx" % "11.2.1"
    ) ++ osNames.flatMap(os =>
      Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "20" classifier os) ),
    crossPaths := false, // https://github.com/sbt/junit-interface/issues/35
    Test / parallelExecution := false
  )


Compile/compile/wartremoverWarnings ++= Warts.allBut(
  Wart.Nothing,
  Wart.Throw,
  Wart.Var,
  Wart.SeqApply,
  Wart.SeqUpdated,
  Wart.IterableOps,
  Wart.MutableDataStructures,
  Wart.ImplicitConversion,
  Wart.Overloading,
  Wart.ToString,
  Wart.SizeIs
)

assembly / mainClass := Some("it.unibo.pps.duckgame.Main")
assembly / assemblyOutputPath := file("target/duckgame.jar")
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}

