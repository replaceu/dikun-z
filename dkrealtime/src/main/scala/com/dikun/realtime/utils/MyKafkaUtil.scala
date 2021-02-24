package com.dikun.realtime.utils

import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

/**读取kafka的工具类*/

object MyKafkaUtil {



    val properties = MyPropertiesUtil.load("config.properties")
    val borker_list = properties.getProperty("kafka.broker.list")

    val kafkaParam = collection.mutable.Map(
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> borker_list,
      //"bootstrap.servers"->borker_list,//集群地址
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> StringDeserializer,
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> StringDeserializer,
      //用于标识这个消费者属于哪个消费团体
      ConsumerConfig.GROUP_ID_CONFIG -> "dkmall_group",
      //偏移量
      //latest 自动重置偏移量为最新的偏移量
      ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "latest",
      ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> (false:java.lang.Boolean)
    )


    // 创建 DStream，返回接收到的输入数据

    // 创建 DStream，返回接收到的输入数据
    def getKafkaStream(topic: String,ssc:StreamingContext ): InputDStream[ConsumerRecord[String,String]]={
      val dStream = KafkaUtils.createDirectStream[String,String](
        ssc,
        LocationStrategies.PreferConsistent,
        ConsumerStrategies.Subscribe[String,String](Array(topic),kafkaParam)
      )
      dStream
    }

    // 创建 DStream，返回接收到的输入数据
    def getKafkaStream(topic: String,ssc:StreamingContext,groupId:String): InputDStream[ConsumerRecord[String,String]]={
      kafkaParam("group.id")=groupId
      val dStream = KafkaUtils.createDirectStream[String,String](
        ssc,
        LocationStrategies.PreferConsistent,
        ConsumerStrategies.Subscribe[String,String](Array(topic),kafkaParam)

      )
      dStream
    }

    def getKafkaStream(topic: String,ssc:StreamingContext,offsets:Map[TopicPartition,Long],groupId:String): InputDStream[ConsumerRecord[String,String]]={
      kafkaParam("group.id")=groupId
      val dStream = KafkaUtils.createDirectStream[String,String](
        ssc,
        LocationStrategies.PreferConsistent,
        ConsumerStrategies.Subscribe[String,String](Array(topic),kafkaParam,offsets))
      dStream
    }



}
