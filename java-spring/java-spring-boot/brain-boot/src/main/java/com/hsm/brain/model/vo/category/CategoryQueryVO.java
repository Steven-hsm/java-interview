package com.hsm.brain.model.vo.category;

import com.hsm.brain.model.vo.PageVO;
import lombok.Data;

/**
 * @Classname CategoryQueryVO
 * @Description 分类查询入参
 * @Date 2021/7/12 15:05
 * @Created by huangsm
 */
@Data
public class CategoryQueryVO extends PageVO {
    /**
     * 分类名称
     */
    private String name;
}
