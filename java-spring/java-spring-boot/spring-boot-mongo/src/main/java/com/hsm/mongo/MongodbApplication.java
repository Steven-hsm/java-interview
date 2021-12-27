package com.hsm.mongo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Slf4j
@SpringBootApplication
@EnableMongoAuditing
public class MongodbApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MongodbApplication.class, args);
        log.info("spring-boot-mongodb-chapter31启动!");
    }
}
