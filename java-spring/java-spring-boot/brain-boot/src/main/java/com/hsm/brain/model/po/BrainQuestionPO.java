package com.hsm.brain.model.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Classname BrainQuestionPO
 * @Description TODO
 * @Date 2021/7/9 18:06
 * @Created by huangsm
 */
@Entity
@Data
@Table(name = "brain_question")
public class BrainQuestionPO {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 内容
     */
    private String content;

    /**
     * 答案
     */
    private String answer;

    /**
     * 添加人
     */
    private Integer userId;

    /**
     * 添加时间
     */
    private Long ctime;
}
