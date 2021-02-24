package com.dikun.realtime.utils

import java.io.InputStreamReader
import java.util.Properties

/**读取properties配置文件类*/


object MyPropertiesUtil {


  def main(args: Array[String]): Unit = {
    val properties = MyPropertiesUtil.load("config.properties")
    println(properties.getProperty("kafka.broker.list"))
  }

  /**从配置文件加载*/
  def load(propertiesName:String): Properties ={
    val prop = new Properties();

    //加载
    prop.load(new
      //获取当前线程的类加载器
        InputStreamReader(Thread.currentThread().getContextClassLoader.
          getResourceAsStream(propertiesName) ,"UTF-8"))
    prop
  }

}
