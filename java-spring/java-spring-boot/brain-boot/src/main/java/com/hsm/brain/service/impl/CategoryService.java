package com.hsm.brain.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsm.brain.mapper.CategoryMapper;
import com.hsm.brain.model.po.CategoryPO;
import com.hsm.brain.model.vo.category.CategoryQueryVO;
import com.hsm.brain.service.ICategoryService;
import com.hsm.brain.utils.SqlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Classname CategoryService
 * @Description TODO
 * @Date 2021/7/9 18:12
 * @Created by huangsm
 */
@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(CategoryPO categoryPO) {

        categoryPO.setCtime(System.currentTimeMillis());
        categoryPO.setUserId(0);
        categoryMapper.insert(categoryPO);
    }

    @Override
    public void update(CategoryPO categoryPO) {
        CategoryPO oleCategory = this.selectById(categoryPO.getId());
        if (oleCategory == null) {
            return;
        }
        BeanUtils.copyProperties(oleCategory, categoryPO);
        categoryMapper.updateById(categoryPO);
    }

    @Override
    public CategoryPO selectById(long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public IPage<CategoryPO> list(CategoryQueryVO categoryQueryVO) {
        Page<CategoryPO> page = new Page<>(categoryQueryVO.getPageNum(), categoryQueryVO.getPageSize());
        //将名称设置为模糊查询
        categoryQueryVO.setName(SqlUtils.contactLike(categoryQueryVO.getName()));
        return categoryMapper.listWithPage(page, categoryQueryVO);
    }
}
