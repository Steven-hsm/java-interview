package com.hsm.brain.model.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Classname Category
 * @Description TODO
 * @Date 2021/7/9 17:58
 * @Created by huangsm
 */
@Entity
@Data
@Table(name = "category")
public class CategoryPO {
    @Id
    private Long id;
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
