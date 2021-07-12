package com.hsm.brain.model.vo.question;

import com.hsm.brain.model.vo.PageVO;
import lombok.Data;

/**
 * @Classname QuestionQueryVO
 * @Description 试题查询入参
 * @Date 2021/7/12 15:48
 * @Created by huangsm
 */
@Data
public class QuestionQueryVO extends PageVO {
    /**
     * 内容
     */
    private String content;
}
