package com.hsm.brain.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname Category
 * @Description TODO
 * @Date 2021/7/9 17:58
 * @Created by huangsm
 */

@TableName("category")
@Data
@ApiModel("分类")
public class CategoryPO {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("分类id")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("添加用户id")
    private Integer userId;

    @ApiModelProperty("添加时间")
    private Long ctime;

}
