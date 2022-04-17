package com.travist.clean

import com.travist.utils.HttpStream
import org.apache.log4j.{Level, Logger}
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

    // Port and streaming trigger interval
    val PORT = 9999
    val INTERVAL = "5 seconds"

    // Create HTTP Server and start streaming
    implicit val sqlContext: SQLContext = spark.sqlContext
    val query = new HttpStream(PORT).toDF
      .writeStream
      .trigger(Trigger.ProcessingTime(INTERVAL))
      .foreachBatch((batch, batchId) => {
        println(s"Processing batch: $batchId")
        batch.show(false)
      })
      .start()

    // Wait for it...
    query.awaitTermination()
  }

  case class Incident(title: String, datetime: String, detail: String)
}
