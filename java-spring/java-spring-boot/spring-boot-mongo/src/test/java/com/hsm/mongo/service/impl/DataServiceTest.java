package com.hsm.mongo.service.impl;

import com.hsm.mongo.MongodbApplication;
import com.hsm.mongo.entity.MyData;
import com.hsm.mongo.service.IDataService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest(classes = MongodbApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class DataServiceTest {

    @Autowired
    private IDataService dataService;
    @Test
    public void saveData() {
        MyData myData = new MyData();
        myData.setName("hsm");
        myData.setAge("26");
        dataService.saveData(myData);
    }
}