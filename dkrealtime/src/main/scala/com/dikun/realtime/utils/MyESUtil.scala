package com.dikun.realtime.utils

import java.util

import com.dikun.realtime.bean.DauInfo
import io.searchbox.client.config.HttpClientConfig
import io.searchbox.client.{JestClient, JestClientFactory}
import io.searchbox.core._


object MyESUtil {
  /**向ES中批量插入数据*/

  //声明Jest客户端工厂
  private var jestFactory:JestClientFactory = null

  def build() = {
    jestFactory=new JestClientFactory
    jestFactory.setHttpClientConfig(new HttpClientConfig.Builder("http://node001:9200" )
      .multiThreaded(true)
      .maxTotalConnection(20)
      .connTimeout(10000).readTimeout(1000).build())
  }

  def getJestClient:JestClient ={
    if(jestFactory==null)build();
    jestFactory.getObject
  }

  def bulkInsert(dauList: List[DauInfo], indexName: String): Unit = {
    if (dauList!=null&&dauList.size!=0) {


      //获取客户
      val jestClient: JestClient = getJestClient

      val bulkBuilder: Bulk.Builder = new Bulk.Builder()

      for (dauInfo<-dauList){
        val index: Index = new Index.Builder(dauInfo)
          .index(indexName)
          .`type`("_doc")
          .build()
        bulkBuilder.addAction(index)
      }

      val bulk: Bulk = bulkBuilder.build()
      val bulkResult: BulkResult = jestClient.execute(bulk)
      println("向ES中插入"+bulkResult.getItems.size()+"条数据")

      jestClient.close()

    }

  }

}
