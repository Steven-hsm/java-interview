package com.hsm.brain.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hsm.brain.WebApplication;
import com.hsm.brain.model.po.CategoryPO;
import com.hsm.brain.model.vo.category.CategoryQueryVO;
import com.hsm.brain.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONValue;
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
        for (int i = 0; i < 100; i++) {
            CategoryPO category = new CategoryPO();
            category.setName("历史类" + i);
            category.setUserId(0);
            category.setCtime(System.currentTimeMillis());
            categoryService.add(category);
        }


    }

    @Test
    public void update() {
    }

    @Test
    public void selectById() {
    }

    @Test
    public void list() {
        CategoryQueryVO categoryQueryVO = new CategoryQueryVO();
        categoryQueryVO.setName("情感");
        categoryQueryVO.setPageNum(1);
        categoryQueryVO.setPageSize(10);
        IPage<CategoryPO> page = categoryService.list(categoryQueryVO);
        System.out.println(JSONValue.toJSONString(page));

        categoryQueryVO.setPageNum(2);
        IPage<CategoryPO> page2 = categoryService.list(categoryQueryVO);
        System.out.println(JSONValue.toJSONString(page2));
    }
}