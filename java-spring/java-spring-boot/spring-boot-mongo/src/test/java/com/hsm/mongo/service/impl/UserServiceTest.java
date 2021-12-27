package com.hsm.mongo.service.impl;
import java.util.Date;

import com.hsm.mongo.MongodbApplication;
import com.hsm.mongo.entity.User;
import com.hsm.mongo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = MongodbApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void save(){
        User user = new User();
        user.setName("hsm");
        user.setAge("10");
        user.setGmtCreate(new Date());
        userService.save(user);

    }
}