package com.travist.clean

import org.apache.spark.sql._
import org.apache.log4j._
import com.travist.utils.HttpStream
import org.apache.spark.sql.streaming.Trigger

object CleanIncoming {
  def main(args: Array[String]): Unit = {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create Spark Session
    val spark = SparkSession
      .builder
      .appName("Clean incoming request")
      .master("local[*]")
      .getOrCreate()

    // Port and streaming trigger interval
    val PORT = 9999
    val INTERVAL = "5 seconds"

    def printOut(batch:DataFrame, batchId:Long): Unit = {
      println(s"Processing batch: $batchId")
      batch.show(false)
    }

    // Create HTTP Server and start streaming
    implicit val sqlContext: SQLContext = spark.sqlContext
    val query = new HttpStream( PORT ).toDF
      .writeStream
      .trigger(Trigger.ProcessingTime(INTERVAL))
      .foreachBatch(printOut _)
      .start()

    // Wait for it...
    query.awaitTermination()

    // Stop the session
    spark.stop()
  }
}
