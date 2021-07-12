package com.hsm.brain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsm.brain.model.po.CategoryPO;
import com.hsm.brain.model.vo.category.CategoryQueryVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Classname CategoryMapper
 * @Description TODO
 * @Date 2021/7/10 15:08
 * @Created by huangsm
 */
public interface CategoryMapper extends BaseMapper<CategoryPO> {

    /**
     * 分页获取列表数据
     *
     * @param page
     * @param categoryVo
     * @return
     */
    IPage<CategoryPO> listWithPage(Page<CategoryPO> page, @Param("vo") CategoryQueryVO categoryVo);
}
