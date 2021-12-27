package com.hsm.mongo.service.impl;

import com.hsm.mongo.entity.MyData;
import com.hsm.mongo.service.IDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataService implements IDataService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Override
    public void saveData(MyData myData) {
        mongoTemplate.save(myData);
    }
}
