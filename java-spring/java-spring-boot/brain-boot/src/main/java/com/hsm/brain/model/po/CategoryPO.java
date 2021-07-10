package com.hsm.brain.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Classname Category
 * @Description TODO
 * @Date 2021/7/9 17:58
 * @Created by huangsm
 */

@TableName("category")
@Data
public class CategoryPO {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 添加用户id
     */
    private Integer userId;

    /**
     * 添加时间
     */
    private Long ctime;

}
