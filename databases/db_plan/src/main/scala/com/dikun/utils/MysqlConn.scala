package com.dikun.utils

import java.util.Properties

object MysqlConn {
  //2、读取mysql表中数据
  //2.1 定义url连接
  val url="jdbc:mysql://localhost:3306/spark"
  //2.2 定义表名
  //val table="sale"
  //2.3 定义属性
  val properties=new Properties()
  properties.setProperty("user","root")
  properties.setProperty("password","19940818zj")



}
