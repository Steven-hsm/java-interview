package com.hsm.service.impl;

import com.hsm.entity.User;
import com.hsm.mapper.UserMapper;
import com.hsm.service.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;
    /**
     * @Description:
     * 事务传播性：
     *  当一个事务方法被另一个事务方法调用时，这个事务方法应该如何进行
     *  1. PROPAGATION_REQUIRED 如果存在一个事务，则支持当前事务。如果没有事务则开启一个新的事务。
     *  2. PROPAGATION_SUPPORTS 如果存在一个事务，支持当前事务。如果没有事务，则非事务的执行。
     *  3. PROPAGATION_MANDATORY 如果已经存在一个事务，支持当前事务。如果没有一个活动的事务，则抛出异常。
     *  4. PROPAGATION_REQUIRED_NEW 使用PROPAGATION_REQUIRES_NEW,需要使用 JtaTransactionManager作为事务管理器。 它会开启一个新的事务。如果一个事务已经存在，则先将这个存在的事务挂起。
     *  5. PROPAGATION_NOT_SUPPORTED PROPAGATION_NOT_SUPPORTED 总是非事务地执行，并挂起任何存在的事务。使用PROPAGATION_NOT_SUPPORTED,也需要使用JtaTransactionManager作为事务管理器。
     *  6. PROPAGATION_NEVER 总是非事务地执行，如果存在一个活动事务，则抛出异常。
     *  7. PROPAGATION_NESTED 如果一个活动的事务存在，则运行在一个嵌套的事务中。 如果没有活动事务, 则按TransactionDefinition.PROPAGATION_REQUIRED 属性执行。
     * @Author: huangsm
     * @Date: 2021/3/20
     **/
    @Test
    void transactionPropagation1() {
        User user = userMapper.selectById(1);
        user.setName("update1");
        user.setAge(100);
        userService.update(user);

    }
}