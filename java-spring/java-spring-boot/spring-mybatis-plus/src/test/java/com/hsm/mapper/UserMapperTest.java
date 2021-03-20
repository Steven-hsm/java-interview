package com.hsm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsm.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- 普通查询 test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void pageSelect() {
        System.out.println(("----- 分页查询 test ------"));
        Page<User> userPage = userMapper.selectPage(new Page<User>(0, 3), null);

        Assert.assertEquals(3, userPage.getRecords().size());
        userPage.getRecords().forEach(System.out::println);
    }

    @Test
    public void optimismLock() {
        System.out.println(("----- 乐观锁 test ------"));
        User user = userMapper.selectById(1);
        Integer version = user.getVersion();
        System.out.println(version);
        userMapper.updateById(user);
        System.out.println(user.getVersion());
    }

}