name := """advanced-scala-exercises"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.2.0",
  "org.scalaz" %% "scalaz-concurrent" % "7.2.0",
  "org.scalaz.stream" %% "scalaz-stream" % "0.8"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.7.1")