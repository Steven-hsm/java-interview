package com.hsm.brain.service.impl;

import com.hsm.brain.WebApplication;
import com.hsm.brain.model.po.CategoryPO;
import com.hsm.brain.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Classname CategoryServiceTest
 * @Description TODO
 * @Date 2021/7/10 16:28
 * @Created by huangsm
 */
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class CategoryServiceTest {
    @Autowired
    private ICategoryService categoryService;

    @Test
    public void add() {
        CategoryPO category = new CategoryPO();
        category.setName("情感类");
        category.setUserId(0);
        category.setCtime(System.currentTimeMillis());
        categoryService.add(category);
    }

    @Test
    public void update() {
    }

    @Test
    public void selectById() {
    }
}