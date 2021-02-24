package com.dikun.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.dikun.oss.service.OssService;
import com.dikun.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService{
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            //调用oss方法实现上传
            //获取文件的名称
            String filename = file.getOriginalFilename();
            //在文件名称后面使用唯一名称值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");

            filename = uuid + filename;

            //将文件按照日期进行分类
            //获取当前的日期
            String datePath = new DateTime().toString("yyyy/MM/dd");

            filename = datePath+"/"+filename;
            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;
            return url;

        }catch (Exception e){
            e.printStackTrace();
            return null;

        }
        //https://dikun-goods001.oss-cn-shenzhen.aliyuncs.com/6291.jpg
    }
}
