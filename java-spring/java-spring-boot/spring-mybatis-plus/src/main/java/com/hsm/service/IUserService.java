package com.hsm.service;

import com.hsm.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUserService {
    /**
     * @Description: 批量添加
     * @Author: huangsm
     * @Date: 2021/3/20
     **/
    boolean batchAdd1(List<User> userList);

    /**
     * 批量删除
     * @param idList
     * @return
     */
    boolean batchDelete1(List<Long> idList);

    /**
     * 更新名称
     * @param id
     * @param name
     * @return
     */
    int updateName(long id,String name);

    /**
     * 更新年龄
     * @param id
     * @param age
     * @return
     */
    int updateAge(long id,int age);

    /**
     * 更新邮箱
     * @param id
     * @param email
     * @return
     */
    int updateEmail(long id,String email);

    /**
     * 更新
     * @param user
     * @return
     */
    void update(User user);

}
