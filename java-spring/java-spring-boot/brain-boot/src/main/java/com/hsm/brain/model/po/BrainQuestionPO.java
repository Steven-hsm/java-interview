package com.hsm.brain.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Classname BrainQuestionPO
 * @Description TODO
 * @Date 2021/7/9 18:06
 * @Created by huangsm
 */

@Data
@TableName("brain_question")
public class BrainQuestionPO {
    /**
     * 主键
     */
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
