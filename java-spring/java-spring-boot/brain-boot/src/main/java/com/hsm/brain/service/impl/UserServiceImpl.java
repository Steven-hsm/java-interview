package com.hsm.brain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsm.brain.config.UserContext;
import com.hsm.brain.mapper.UserMapper;
import com.hsm.brain.model.po.UserPO;
import com.hsm.brain.service.IUserService;
import com.hsm.brain.utils.DateUtils;
import com.hsm.brain.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author huangsm
 * @since 2021-08-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public String login(String openId) {
        UserPO user= userMapper.selectByOpentId(openId);
        if(user == null){
            user = new UserPO();
            user.setPhone("");
            user.setUnionId("");
            user.setOpenId(openId);
            user.setNickName("");
            user.setCtime(System.currentTimeMillis());
            this.save(user);
        }
        return this.createToken(user);
    }

    @Override
    public String createToken(UserPO user) {
        return JWTUtils.createToken(user, DateUtils.getIntervalDays(1));
    }

    @Override
    public UserPO parseToken(String token) {
        Claims claims = JWTUtils.parseToken(token);
        String openId = (String)claims.get("openId");
        return userMapper.selectByOpentId(openId);
    }
}
