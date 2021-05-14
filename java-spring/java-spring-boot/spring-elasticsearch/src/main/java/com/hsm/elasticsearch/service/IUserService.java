package com.hsm.elasticsearch.service;

import com.hsm.elasticsearch.entity.UserESPO;

/**
 * @description: 用户接口
 * @author: huangsm
 * @createDate: 2021/3/16
 */
public interface IUserService {
    /**
     * @Description:添加用户
     * @Author: huangsm
     * @Date: 2021/3/16
     **/
    boolean add(UserESPO user);

    /**
     * 删除索引
     * @return
     */
    void dropIndex();

    /**
     * 创建索引
     * @return
     */
    void createIndex();
}
