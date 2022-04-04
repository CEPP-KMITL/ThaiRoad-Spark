name := "TravistSpark"

version := "0.1.0"

scalaVersion := "2.12.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.2.1" % "provided",
  "org.apache.spark" %% "spark-sql" % "3.2.1" % "provided",
  "org.apache.spark" %% "spark-mllib" % "3.2.1" % "provided",
  "org.apache.spark" %% "spark-streaming" % "3.2.1" % "provided"
)
