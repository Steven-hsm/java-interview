package com.hsm.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("user")
@Data
public class User {
    @Id
    String id;
    @Indexed
    String name;
    @Indexed
    String age;

    @CreatedDate
    Date gmtCreate;

}
