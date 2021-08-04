package com.hsm.brain.config;

import com.hsm.brain.model.po.UserPO;

/**
 * @Classname UserContext
 * @Description 用户信息尚希文
 * @Date 2021/8/4 17:35
 * @Created by huangsm
 */
public class UserContext {
    private static ThreadLocal<UserPO> user = new ThreadLocal<>();

    public static final UserPO getUser() {
        return user.get();
    }

    public static final void setUser(UserPO userVo) {
        user.set(userVo);
    }

    public static final void removeUser() {
        user.remove();
    }
}
