package com.dikun.realtime.utils

import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig}

/**获取Redis*/
object MyRedisUtil {
  //定义一个连接池对象
  private var jedisPool:JedisPool = null;

  def build():Unit = {

    val prop = MyPropertiesUtil.load("config.properties")
    val host = prop.getProperty("redis.host")
    val port = prop.getProperty("redis.port")

    val config = new JedisPoolConfig
    config.setMaxTotal(100)//最大连接数
    config.setBlockWhenExhausted(true)//忙碌时是否等待
    config.setTestOnBorrow(true)//每次获得连接进行测试
    config.setMaxIdle(20)//最大空闲
    config.setMinIdle(20)//最小空闲
    config.setMaxWaitMillis(5000)//忙碌时等待时长

    jedisPool = new JedisPool(config,host,port.toInt)
  }

  //获取jedis客户端
  def getJedisClient(): Jedis ={
    if (jedisPool==null){
      build()
    }
    jedisPool.getResource
  }

  def main(args: Array[String]): Unit = {
    val jedisClient = getJedisClient
    println(jedisClient.ping())
    jedisClient.close()
  }
}
