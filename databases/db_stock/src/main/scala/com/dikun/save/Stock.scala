package com.dikun.save

case class Stock (
                   date:Long,
                   goodsName:String,
                   color:String,
                   size:String,
                   /**写到成品仓库存*/
                   stocktotal:Int,
                   stock092:Int,
                   stock107:Int,
                   stock108:Int,
                   stock168:Int,
                   stockB30:Int,
                   stocksum:Int
                 )
