package com.hsm.brain.repository;

import com.hsm.brain.model.po.CategoryPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Classname CategoryRepository
 * @Description TODO
 * @Date 2021/7/9 18:10
 * @Created by huangsm
 */
public interface CategoryRepository extends JpaRepository<CategoryPO, Integer> {

}
