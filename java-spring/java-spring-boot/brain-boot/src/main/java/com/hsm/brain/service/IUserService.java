package com.hsm.brain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hsm.brain.model.po.UserPO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangsm
 * @since 2021-08-04
 */
public interface IUserService extends IService<UserPO> {
    /**
     * 用户登录
     * @param openId
     * @return
     */
    String login(String openId);

    /**
     * 创建token
     * @param user
     * @return
     */
    String createToken(UserPO user);

    /**
     * 解析Token
     * @param token
     * @return
     */
    UserPO parseToken(String token);
}
