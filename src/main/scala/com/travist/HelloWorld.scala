package com.travist

import org.apache.spark.sql._

object HelloWorld {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.master("spark://localhost:7077").appName("SparkByExample").getOrCreate

    println("First SparkContext:")
    println("APP Name :" + spark.sparkContext.appName)
    println("Deploy Mode :" + spark.sparkContext.deployMode)
    println("Master :" + spark.sparkContext.master)
  }
}
