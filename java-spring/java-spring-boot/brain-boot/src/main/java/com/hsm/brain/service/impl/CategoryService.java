package com.hsm.brain.service.impl;

import com.hsm.brain.model.po.CategoryPO;
import com.hsm.brain.service.ICategoryService;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    JPAQueryFactory queryFactory;

    @Override
    public void add(CategoryPO categoryPO) {
        //queryFactory.selectFrom()
    }
}
