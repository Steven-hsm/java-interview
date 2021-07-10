package com.hsm.brain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname WebApplication
 * @Description TODO
 * @Date 2021/7/9 18:07
 * @Created by huangsm
 */
@SpringBootApplication
@MapperScan("com.hsm.brain.mapper")
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
