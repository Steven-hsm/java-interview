package com.hsm.brain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hsm.brain.model.po.CategoryPO;
import com.hsm.brain.model.vo.category.CategoryQueryVO;

/**
 * @Classname ICategoryService
 * @Description 分类服务
 * @Date 2021/7/9 18:12
 * @Created by huangsm
 */
public interface ICategoryService {
    /**
     * 添加分类
     * @param categoryPO
     */
    void add(CategoryPO categoryPO);

    /**
     * 更新分类
     * @param categoryPO
     */
    void update(CategoryPO categoryPO);

    /**
     * 根据主键获取数据
     * @param id
     */
    CategoryPO selectById(long id);

    /**
     * 查询分类列表数据
     * @param categoryQueryVO
     * @return
     */
    IPage<CategoryPO> list(CategoryQueryVO categoryQueryVO);
}
