package com.travist.clean

import com.travist.utils.HttpStream
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.DateType
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

      val dfJsonValue = batch
        .withColumn("i", from_json(col("value"), schema))

      val titleExp = "[\"“][ ]*(.*)[\\s][\"”]"
      val dfTitle = dfJsonValue
        .withColumn("title", regexp_replace($"i.title", "[\\\"]", "\""))
        .withColumn("title", regexp_extract(col("title"), titleExp, 1))

      val districtExp = "อ\\.(\\S+)"
      val provinceExp = "จ\\.(\\S+)"
      val dfLocation = dfTitle
        .withColumn("district", regexp_extract(col("i.title"), districtExp, 1))
        .withColumn("province", regexp_extract(col("i.title"), provinceExp, 1))

      val dateExp = "วันที่[\\s]+(\\S*)[\\s]+\\S*[\\s]+(\\S*)น."
      val customDateFormat = "dd/MM/yyyyHH.mm"
      val dfDate = dfLocation
        .withColumn("date", regexp_extract(col("i.title"), dateExp, 1))
        .withColumn("time", regexp_extract(col("i.title"), dateExp, 2))
        .withColumn("dateTimeStamp", concat_ws("", col("date"), col("time")))
        .withColumn("dateTimeStamp", to_timestamp(col("dateTimeStamp"), customDateFormat))
        .withColumn("dateTimeEpoch", $"dateTimeStamp".cast("long"))
        .withColumn("dateTimeTruncated", unix_timestamp(col("dateTimeStamp").cast(DateType)))
        .withColumn("dateTimeMillisDiff", col("dateTimeEpoch") - col("dateTimeTruncated"))
        .withColumn("dateTimeStampAC", add_months(col("dateTimeStamp"), -6516))
        .withColumn("dateTimeEpochAC", unix_timestamp(col("dateTimeStampAC")) + col("dateTimeMillisDiff"))
        .withColumn("dateTimeStamp", to_timestamp(col("dateTimeEpochAC")))
        .drop("date")
        .drop("time")
        .drop("dateTimeEpoch")
        .drop("dateTimeTruncated")
        .drop("dateTimeMillisDiff")
        .drop("dateTimeStampAC")
        .drop("dateTimeEpochAC")
        .withColumnRenamed("dateTimeStamp", "timeOfOccurrence")

      val fatalityExp = "เสียชีวิต[\\s]*(\\S*)[\\s]*ราย"
      val casualtyExp = "บาดเจ็บ[\\s]*(\\S*)[\\s]*คน"
      val dfFC = dfDate
        .withColumn("fatality", regexp_extract(col("i.title"), fatalityExp, 1))
        .withColumn("casualty", regexp_extract(col("i.title"), casualtyExp, 1))

      //      val dfDetail = dfDate
      //        .withColumn("detail", col("i.detail"))

      val dfDropNonUseColumns = dfFC.drop("value").drop("i")
      dfDropNonUseColumns.show(false)
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
