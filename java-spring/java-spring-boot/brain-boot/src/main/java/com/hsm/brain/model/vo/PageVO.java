package com.hsm.brain.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Classname PageVO
 * @Description TODO
 * @Date 2021/7/12 14:55
 * @Created by huangsm
 */
@Data
public class PageVO {

    @NotNull(message = "页数不能为空")
    @Min(value = 1,message = "最小页数必须不小于1")
    @ApiModelProperty("当前页码")
    private Integer pageNum = 1;

    @NotNull(message = "每页条数不能为空")
    @Min(value = 10,message = "最小每页条数不小于10")
    @ApiModelProperty("每页大小")
    private Integer pageSize = 10;
}
