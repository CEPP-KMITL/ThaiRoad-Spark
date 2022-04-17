name := "TravistSpark"

version := "0.1.0"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.5" % "provided",
  "org.apache.spark" %% "spark-sql" % "2.4.5" % "provided",
  "org.apache.spark" %% "spark-mllib" % "2.4.5" % "provided",
  "org.apache.spark" %% "spark-streaming" % "2.4.5" % "provided"
)
