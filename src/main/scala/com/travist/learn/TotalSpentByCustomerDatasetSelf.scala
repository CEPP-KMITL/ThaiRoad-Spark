package com.travist.learn

import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{FloatType, StringType, StructType}

object TotalSpentByCustomerDatasetSelf {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sparkSession = SparkSession.builder().appName("TotalSpentByCustomerDatasetSelf").master("local[*]").getOrCreate()

    val spentByCustomerSchema = new StructType().add("customerID", StringType, nullable = true).add("productID", StringType, nullable = true)
      .add("price", FloatType, nullable = true)

    import sparkSession.implicits._
    val dataSet = sparkSession.read.schema(spentByCustomerSchema).csv("data/customer-orders.csv").as[CustomerOrder]

    val groupByCustomerID = dataSet.groupBy("customerID").agg(round(sum("price"), 2).as("totalPrice"))


    val sortedGroupByCustomerID = groupByCustomerID.sort("totalPrice")

    sortedGroupByCustomerID.show(sortedGroupByCustomerID.count().toInt)

  }

  case class CustomerOrder(customerID: String, productID: String, price: Float)
}
