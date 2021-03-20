package com.hsm.service.impl;

import com.hsm.entity.User;
import com.hsm.mapper.UserMapper;
import com.hsm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: huangsm
 * @createDate: 2021/3/20
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean batchAdd1(List<User> userList) {
        userList.forEach(user -> {
            userMapper.insert(user);
        });
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean batchDelete1(List<Long> idList) {
        idList.forEach(id -> {
            userMapper.deleteById(id);
        });
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateName(long id, String name) {
        User user = userMapper.selectById(id);
        user.setName(name);
        return userMapper.updateById(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateAge(long id, int age) {
        User user = userMapper.selectById(id);
        user.setAge(age);
        return userMapper.updateById(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateEmail(long id, String email) {
        User user = userMapper.selectById(id);
        user.setEmail(email);
        userMapper.updateById(user);
        throw new IllegalArgumentException("");
        //return
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(User user) {
        updateName(user.getId(), user.getName());
        updateAge(user.getId(), user.getAge());
        updateEmail(user.getId(), user.getEmail());

    }
}
