package com.dikun.process

import java.text.SimpleDateFormat

import com.dikun.save.Plan
import com.dikun.utils.MysqlConn
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object PlanProcess {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("plan").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val planStream = sc.textFile("D:\\typorafile\\flinksource\\DikunAnalysis\\DSparkstreaming\\src\\main\\resources\\plan_data.txt")

    val planData = planStream.map(line => {
      val lineArray = line.split("\t")
      /**用以处理日期*/
      val date = new SimpleDateFormat("yyyy-MM-dd").parse(lineArray(2)).getTime
      Plan(lineArray(0), lineArray(1), date, lineArray(3), lineArray(4), lineArray(5).toInt,
        lineArray(6).toInt, lineArray(7).toInt, lineArray(8).toInt, lineArray(9).toInt, lineArray(10).toInt)
    })

    val spark = SparkSession.builder().appName("PlanData").master("local[*]").getOrCreate()

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
    val dataFrame = planData.toDF()
    dataFrame.createTempView("plan_number")

    /**
      *         goodsName:String,
      *document:String, //单据号码
      *date:String,
      *color:String,
      *shopname:String,
      *S:Int,
      *M:Int,
      *L:Int,
      *XL:Int,
      *XXL:Int,
      *number:Int
      *
      **/
    /***查询每个款的码数，明细*/
    val result1 = spark.sql(
      """
        |select goodsName,sum(S) as S,sum(M) as M,sum(L) as L,
        |sum(XL) as XL,sum(XXL) as XXL,sum(number) as number
        |from plan_number
        |group by goodsName
      """.stripMargin)
    result1.show()
    val table = "plan"
    val mysqlDF = spark.read.jdbc(MysqlConn.url,table,MysqlConn.properties)

    result1.write.mode("append").jdbc(MysqlConn.url,"plan_number",MysqlConn.properties)

    /**计算每个日期下单款数以及数量*/
    val result2 = spark.sql(
      """
        |select date,shopName,
        |count(goodsName) as count_goodsName,
        |sum(number) as sum_number
        |from plan_number
        |group by date,shopName
        |order by date desc
      """.stripMargin)

    result2.show()

    val rdd2 = dataFrame.rdd
    val ssc = new StreamingContext(sc,Seconds(3600*3*24))

    val rddQueue = new mutable.Queue[RDD[Int]]()
    val inputStream = ssc.queueStream(rddQueue,false)

    spark.stop()

  }
}
