package com.yjj.campustradeplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yjj.campustradeplatform.mapper")
public class CampusTradePlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusTradePlatformApplication.class, args);
    }
}