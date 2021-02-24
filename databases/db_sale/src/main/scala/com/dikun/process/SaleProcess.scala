package com.dikun.process

import java.text.SimpleDateFormat

import com.dikun.save.Sale
import com.dikun.utils.ShopName
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object SaleProcess {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SaleProcess")
    val sc = new SparkContext(sparkConf)
    val saleStream = sc.textFile("D:\\typorafile\\flinksource\\DikunAnalysis\\DSparkstreaming\\src\\main\\resources\\sale_data.txt")
    val saleData = saleStream.map(lines => {
      val lineArray = lines.split(",")
      val date = new SimpleDateFormat("yyyy-MM-dd").parse(lineArray(3)).getTime
      Sale(lineArray(0), lineArray(1), ShopName.getShopname(lineArray(2)),
        date, lineArray(4).toInt, lineArray(5).toLong,
        lineArray(6).toFloat)
    })

    val spark = SparkSession.builder().appName("SaleProcess").master("local[*]").getOrCreate()

    //    //2、读取mysql表中数据
    //    //2.1 定义url连接
    //    val url="jdbc:mysql://localhost:3306/spark"
    //    //2.2 定义表名
    //    val table="sale"
    //    //2.3 定义属性
    //    val properties=new Properties()
    //    properties.setProperty("user","root")
    //    properties.setProperty("password","19940818zj")
    //
    //    val mysqlDF= spark.read.jdbc(url,table,properties)
    import spark.implicits._
    val dataFrame = saleData.toDF()
    dataFrame.createTempView("sale_number")
    //dataFrame.show()
    val result1 = spark.sql(
      """|SELECT
         |goodsName,
         |sum(saleNumber) as saleNumber,
         |sum(saleTotal) as saleAmount
         |FROM sale_number
         |group by goodsName""".stripMargin)
    //result1.write.mode("append").jdbc(url,"sale_number",properties)
    result1.show()
    //
    val result2 = spark.sql(
      """|select
         |date,
         |sum(saleNumber) as dateNumber,
         |sum(saleTotal) as dateAmount
         |from sale_number
         |group by
         |date
      """.stripMargin)

    result2.show()
    //result2.write.mode("append").jdbc(url,"sale_date",properties)

    val result3 = spark.sql(
      """|select
         |shopName,
         |sum(saleNumber) as shopNumber,
         |sum(saleTotal) as shopAmount
         |from sale_number
         |group by
         |shopName
         |order by
         |shopAmount desc
      """.stripMargin)
    result3.show()

    spark.stop()
  }

}
