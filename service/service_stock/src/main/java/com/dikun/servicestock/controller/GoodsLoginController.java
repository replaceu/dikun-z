package com.dikun.servicestock.controller;


import commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicestock/user")
@CrossOrigin
public class GoodsLoginController {
    //login()
    @PostMapping("login")

    public R login(){
        return R.ok().data("token","admin");
    }

    //info()
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]")
                .data("name","admin")
                .data("avatar","C://192.168.0.102//2012年生产资料//生产资料//共享//picture1//1022.jpg");
    }


}
