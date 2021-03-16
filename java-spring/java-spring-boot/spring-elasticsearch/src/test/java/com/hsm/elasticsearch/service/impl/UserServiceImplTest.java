package com.hsm.elasticsearch.service.impl;

import com.hsm.elasticsearch.entity.ChinaNamePO;
import com.hsm.elasticsearch.entity.UserPO;
import com.hsm.elasticsearch.service.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zxp.esclientrhl.repository.GeoEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private IUserService userService;
    @Test
    void add() {
        UserPO user = new UserPO();
        GeoEntity gp1 = new GeoEntity(123.23,123.233);

        ChinaNamePO chinaName = new ChinaNamePO();
        chinaName.setFirstName("张");
        chinaName.setLastName("三丰");

        user.setUserCode(UUID.randomUUID().toString());
        user.setAge(15);
        user.setUserName("张三131");
        user.setGeo(gp1);
        user.setChinaName(chinaName);
        userService.add(user);
    }
}