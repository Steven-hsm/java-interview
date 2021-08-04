package com.hsm.brain.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangsm
 * @since 2021-08-04
 */
@Data
@TableName("user")
@EqualsAndHashCode(callSuper = false)
public class UserPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * unionId
     */
    private String unionId;

    /**
     * openId
     */
    private String openId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 创建时间
     */
    private Long ctime;


}
