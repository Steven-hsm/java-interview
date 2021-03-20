package com.hsm.entity;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * @description:
 * @author: huangsm
 * @createDate: 2021/3/20
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    @Version
    private Integer version;
}
