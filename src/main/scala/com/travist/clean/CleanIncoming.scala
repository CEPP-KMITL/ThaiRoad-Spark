package com.travist.clean

import com.travist.utils.HttpStream
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{SQLContext, SparkSession}

object CleanIncoming {

  def main(args: Array[String]): Unit = {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)
    // Create Spark Session
    val spark = SparkSession
      .builder
      .master("local[*]")
      .appName(getClass.getSimpleName)
      .getOrCreate

    import spark.implicits._

    // Port and streaming trigger interval
    val PORT = 9999
    val INTERVAL = "5 seconds"

    // Create HTTP Server and start streaming
    implicit val sqlContext: SQLContext = spark.sqlContext
    val schema = spark.read.option("multiLine", value = true).json("./data/schema/thairsc.json").as[Incident].schema
    val queryDF = new HttpStream(PORT).toDF(sqlContext).withColumn("i", from_json(col("value"), schema))
      .withColumn("title", col("i.title"))
      .withColumn("datetime", col("i.datetime"))
      .withColumn("detail", col("i.detail"))

    println(schema)

    val query = queryDF.writeStream.outputMode("append")
      .format("console")
      .option("truncate", value = false)
      .trigger(Trigger.ProcessingTime(INTERVAL)).start()

    // Wait for it...
    query.awaitTermination()
  }

  case class Incident(title: String, datetime: String, detail: String)
}
