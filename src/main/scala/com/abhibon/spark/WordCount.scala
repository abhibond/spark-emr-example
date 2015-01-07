/**
 * Created by abhinav on 1/7/15.
 */
package com.abhibon.spark

import org.apache.spark.{SparkConf, SparkContext}
import SparkContext._


object WordCount {

  private def tokenize(text : String) : Array[String] = {
    // Lowercase each word and remove punctuation.
    text.toLowerCase.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+")
  }

  def main(args: Array[String]) {
    val logFile = args(0) // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
    val words = sc.textFile(args(0)).flatMap(line => tokenize(line)).map(x => (x, 1))
    val wordCounts = words.reduceByKey(_ + _)
    wordCounts.saveAsTextFile(args(1))
  }
}
