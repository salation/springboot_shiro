package com.example.shiro_practice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
//@MapperScan("com.example.shiro_practice.mapper")
public class ShiroPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroPracticeApplication.class, args);
    }

}
