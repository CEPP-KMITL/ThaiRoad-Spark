package com.travist.learn

import org.apache.log4j._
import org.apache.spark._


object TotalSpentByCustomerSelf {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc = new SparkContext("local[*]", "TotalAmount")
    val input = sc.textFile("data/customer-orders.csv")
    val parsedLines = input.map(parseLine)
    val amountCount = parsedLines.reduceByKey((x, y) => x + y)
    val flipped = amountCount.map(x => (x._2, x._1))
    val totalByCustomerSorted = flipped.sortByKey()
    val results = totalByCustomerSorted.collect()
    results.foreach(println)
  }

  def parseLine(line: String): (Int, Float) = {
    val fields = line.split(",")
    val customerID = fields(0).toInt
    val dollarAmount = fields(2).toFloat
    (customerID, dollarAmount)
  }
}
