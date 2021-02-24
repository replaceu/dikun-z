package com.dikun.realtime.app

import java.lang
import java.sql.Date
import java.text.SimpleDateFormat

import com.alibaba.fastjson.{JSON, JSONObject}
import com.dikun.realtime.bean.DauInfo
import com.dikun.realtime.utils.{MyESUtil, MyKafkaUtil, MyRedisUtil}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer


/**日活*/

object DauApp {
  def main(args: Array[String]): Unit = {

    var topic:String="dimall_start"
    var groupId:String="mall_dau"

    val conf = new SparkConf().setAppName("DauApp").setMaster("local[4]")
    val ssc = new StreamingContext(conf,Seconds(5))

    /**通过sparkstreaming程序从kafka中读取数据*/
    val kafkaDStream = MyKafkaUtil.getKafkaStream(topic,ssc,groupId)
    //val jsonDStream = kafkaDStream.map(_.value())

    val jsonObjDStream = kafkaDStream.map(record => {
      val jsonString: String = record.value()
      //将json格式字符串转换为json对象
      val jsonObject = JSON.parseObject(jsonString)
      //从json对象中获取时间戳
      val ts = jsonObject.getLong("ts")
      //将时间戳转换为日期和小时
      val dateStr = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date(ts))

      val dateStrArr = dateStr.split(" ")

      val dt = dateStrArr(0)
      val hr = dateStrArr(1)

      jsonObject.put("dt", dt)
      jsonObject.put("hr", hr)
      jsonObject

    })
    jsonObjDStream

    //对Redis采集到的的启动日志进行去重
    /**
      * Redis
      *  String
      *  List
      *  Hash
      *  Set:key:20201023
      *      value:mid18,mid19
      *  zSet*/

    /**val filteredDStream = jsonObjDStream.filter {
      jsonObj => {
        //获取登录日期
        val dt = jsonObj.getString("dt")
        //获取设备ID
        val mid = jsonObj.getJSONObject("common").getString("mid")
        //拼接Redis中保存登录信息的key
        val dauKey = "dau:" + dt
        //获取Redis客户端
        val jedis = MyRedisUtil.getJedisClient()
        //从Redis判断是否登录过
        val isFirst = jedis.sadd(dauKey, mid)
        //设置key的失效时间
        if (jedis.ttl(dauKey) < 0) {
          jedis.expire(dauKey, 3600 * 24)

        }
        if (isFirst == 1L) {
          //说明是第一次登录
          true
        } else {
          false
        }
      }
    }
    filteredDStream*/

    /**以分区为单位对数据进行处理，减少Redis连接次数*/
    val filteredDStream: DStream[JSONObject] = jsonObjDStream.mapPartitions {
      jsdonObjItr => {
        val jedis = MyRedisUtil.getJedisClient()

        var filterlistBuffer: ListBuffer[JSONObject] = new ListBuffer[JSONObject]()

        //对分区数据进行遍历
        for (jsonObj <- jsdonObjItr) {
          //获取日期
          val dt = jsonObj.getString("dt")
          //获取设备ID
          var mid: String = jsonObj.getJSONObject("commom").getString("mid")
          //拼接
          var dauKey: String = "dau" + dt
          //判断是否登录过
          var isFirst: lang.Long = jedis.sadd(dauKey, mid)
          if (jedis.ttl(dauKey) < 0) {
            jedis.expire(dauKey, 3600 * 24)
          }
          if (isFirst == 1L) {
            filterlistBuffer.append(jsonObj)
          }

          jedis.close()

        }
        filterlistBuffer.toIterator
      }
    }

    /**向ElasticSearch中批量保存数据*/
    //===============向 ES 中保存数据===================
    filteredDStream.foreachRDD{
      rdd=>{//获取 DS 中的 RDD
        rdd.foreachPartition{//以分区为单位对 RDD 中的数据进行处理，方便批量插入
          jsonItr=>{
            val dauList: List[DauInfo] = jsonItr.map {
              jsonObj => {
                //每次处理的是一个 json 对象 将 json 对象封装为样例类
                val commonJsonObj: JSONObject = jsonObj.getJSONObject("common")
                DauInfo(
                  commonJsonObj.getString("mid"),
                  commonJsonObj.getString("uid"),
                  commonJsonObj.getString("ar"),
                  commonJsonObj.getString("ch"),
                  commonJsonObj.getString("vc"),
                  jsonObj.getString("dt"),
                  jsonObj.getString("hr"),
                  "00", //分钟我们前面没有转换，默认 00
                  jsonObj.getLong("ts")
                )
              }
            }.toList
            //对分区的数据进行批量处理
            //获取当前日志字符串
            val dt: String = new SimpleDateFormat("yyyy-MM-dd").format(new Date())
            MyESUtil.bulkInsert(dauList,"gmall2020_dau_info_" + dt)
          }
        }
      } }

    ssc.start()
    ssc.awaitTermination()

  }

}
