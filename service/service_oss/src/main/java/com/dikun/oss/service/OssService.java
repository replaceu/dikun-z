package com.dikun.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    //上传图片到oss



    String uploadFileAvatar(MultipartFile file);
}
