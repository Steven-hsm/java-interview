package com.hsm.brain.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname BrainQuestionPO
 * @Description TODO
 * @Date 2021/7/9 18:06
 * @Created by huangsm
 */

@Data
@TableName("brain_question")
@ApiModel("试题")
public class BrainQuestionPO {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("试题id")
    private Integer id;

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("答案")
    private String answer;

    @ApiModelProperty("添加人")
    private Integer userId;

    @ApiModelProperty("添加时间")
    private Long ctime;
}
