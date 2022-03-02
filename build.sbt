version := "0.1.0"
scalaVersion := "2.12.12"

lazy val root = (project in file("."))
  .settings(
    name := "TRAVIST-Spark"
  )

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.2.1",
  "org.apache.spark" %% "spark-sql" % "3.2.1",
  "org.apache.spark" %% "spark-mllib" % "3.2.1",
  "org.apache.spark" %% "spark-streaming" % "3.2.1",
  "org.twitter4j" % "twitter4j-core" % "4.0.7",
  "org.twitter4j" % "twitter4j-stream" % "4.0.7"
)