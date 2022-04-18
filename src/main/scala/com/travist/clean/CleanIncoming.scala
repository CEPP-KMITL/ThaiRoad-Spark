package com.travist.clean

import com.travist.utils.HttpStream
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}

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

    val schema = spark.read.option("multiLine", value = true).json("./data/schema/thairsc.json").as[Incident].schema

    def process(batch: DataFrame, batchId: Long): Unit = {
      println(s"Processing batch: $batchId")
      val df_with_extract_value = batch.withColumn("i", from_json(col("value"), schema))
        .withColumn("title", col("i.title"))
        .withColumn("datetime", col("i.datetime"))
        .withColumn("detail", col("i.detail"))
      val df_that_drop_original_columns = df_with_extract_value.drop("value").drop("i")
      df_that_drop_original_columns.show()
    }

    // Create HTTP Server and start streaming
    implicit val sqlContext: SQLContext = spark.sqlContext
    val query = new HttpStream(port = 9999)
      .toDF
      .writeStream
      .foreachBatch(process _)
      .start()

    // Wait for it...
    query.awaitTermination()

    // Stop the session
    spark.stop()
  }

  case class Incident(title: String, datetime: String, detail: String)
}
