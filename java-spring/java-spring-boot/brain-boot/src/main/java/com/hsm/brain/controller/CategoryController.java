package com.hsm.brain.controller;

import com.hsm.brain.model.common.Result;
import com.hsm.brain.model.po.CategoryPO;
import com.hsm.brain.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname CategoryController
 * @Description TODO
 * @Date 2021/7/9 18:13
 * @Created by huangsm
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/add")
    private Result add(@RequestBody CategoryPO categoryPO){
        categoryService.add(categoryPO);
        return Result.success();
    }
}
