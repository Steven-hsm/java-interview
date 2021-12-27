package com.hsm.mongo.service.impl;

import com.hsm.mongo.entity.User;
import com.hsm.mongo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean save(User user) {
        User save = mongoTemplate.save(user);
        return save.getId() != null;
    }
}
