package com.hsm.brain.model.vo.question;

import com.hsm.brain.model.vo.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname QuestionQueryVO
 * @Description 试题查询入参
 * @Date 2021/7/12 15:48
 * @Created by huangsm
 */
@Data
@ApiModel("试题查询入参")
public class QuestionQueryVO extends PageVO {
    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("试题内容")
    private String content;
}
