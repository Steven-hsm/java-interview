package com.hsm.brain.repository;

import com.hsm.brain.model.po.BrainQuestionPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Classname BrainQuestionRepository
 * @Description TODO
 * @Date 2021/7/9 18:10
 * @Created by huangsm
 */
public interface BrainQuestionRepository  extends JpaRepository<BrainQuestionPO, Integer> {
}
