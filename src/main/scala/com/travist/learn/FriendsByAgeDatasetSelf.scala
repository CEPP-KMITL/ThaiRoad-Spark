package com.travist.learn

import org.apache.log4j._
import org.apache.spark.sql._

object FriendsByAgeDatasetSelf {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession
      .builder
      .appName("FriendByAgeSelf")
      .master("local[*]")
      .getOrCreate()
    import spark.implicits._
    val people = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/fakefriends.csv")
      .as[Person]
    println("Here is our inferred schema:")
    people.printSchema()
    val friendsByAge = people.select("age", "friends")
    friendsByAge.groupBy("age").avg("friends").show()
  }

  case class Person(id: Int, name: String, age: Int, friends: Int)
}
