package com.hsm.brain.model.po;

import java.time.LocalDateTime;
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
@TableName("user_last_record")
@EqualsAndHashCode(callSuper = false)
public class UserLastRecordPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 当前查看试题id
     */
    private Integer currentQuestionId;

    /**
     * 添加时间
     */
    private Long ctime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
