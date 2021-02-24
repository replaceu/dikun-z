package com.dikun.dkmall.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**接收模拟器生成的数据，并对数据进行处理*/

@RestController
@Slf4j//lombok提供的插件用来处理日志文件

//@RestController=@Controller+@ResponseBody
public class LoggerController {

    //springboot提供的对kafka的支持
    @Autowired//将kafkaTemplate注入到Controller中
    KafkaTemplate kafkaTemplate;

    //todo:将数据发送到http：//localhost：8080/applog

    //提供一个方法，处理模拟生成的数据
    //@RequestBody表示从请求体中获取数据

    @RequestMapping("/applog")

    public String applog(@RequestBody String mockLog){
        //System.out.println(mockLog);
        //落盘
        log.info(mockLog);
        //根据日志的类型发送到kafka的不同主题
        JSONObject jsonObject = JSON.parseObject(mockLog);

        JSONObject startJson = jsonObject.getJSONObject("start");
        if (startJson!=null){
            //启动日志
            kafkaTemplate.send("dkmall_start",mockLog);
        }else {
            //事件日志
            kafkaTemplate.send("dkmall_event",mockLog);
        }
        return mockLog;
    }
}
