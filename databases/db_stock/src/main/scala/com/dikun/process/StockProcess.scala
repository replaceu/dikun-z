package com.dikun.process

import java.text.SimpleDateFormat

import com.dikun.save.Stock
import com.dikun.utils.SizeProcess
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object StockProcess{
  def main(args: Array[String]): Unit = {
    /**获取配置*/
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("StockProcess")
    val sc = new SparkContext(sparkConf)
    val stockStream = sc.textFile("D:\\typorafile\\flinksource\\DikunAnalysis\\DSparkstreaming\\src\\main\\resources\\stock_data.txt")
    val stockData = stockStream.map(lines => {
      val lineArray = lines.split("\t")
      val date = new SimpleDateFormat("yyyy-MM-dd").parse(lineArray(0)).getTime
      Stock(date, lineArray(4), lineArray(5),
        SizeProcess.getSize(lineArray(6)),
        lineArray(7).toInt, lineArray(8).toInt,
        lineArray(9).toInt,lineArray(10).toInt,
        lineArray(11).toInt,lineArray(12).toInt,
        lineArray(13).toInt)
    })
    //stockData.foreach(print)

    val spark = SparkSession.builder().appName("StockProcess").master("local[*]").getOrCreate()
    import spark.implicits._
    val dataFrame = stockData.toDF()

    dataFrame.createTempView("stock_number")

    val result1 = spark.sql(
      """
        |select *
        |from stock_number
      """.stripMargin)

    result1.show()

    val result2 = spark.sql(
      """
        |select goodsName,color,size,
        |sum(stocksum) as stocksum
        |from stock_number
        |group by
        |goodsName,color,size
      """.stripMargin)
    result2.show()

    spark.stop()


  }
}
