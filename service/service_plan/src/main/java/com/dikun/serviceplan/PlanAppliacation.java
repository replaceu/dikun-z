package com.dikun.serviceplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"com.dikun"})
public class PlanAppliacation {
    public static void main(String[] args) {
        SpringApplication.run(PlanAppliacation.class,args);
    }
}
