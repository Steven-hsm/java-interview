package com.hsm.mongo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("data")
@Data
public class MyData {
    @Indexed
    String name;
    @Indexed
    String age;
}
