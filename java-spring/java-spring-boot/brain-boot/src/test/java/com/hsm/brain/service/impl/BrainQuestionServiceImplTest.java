package com.hsm.brain.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hsm.brain.WebApplication;
import com.hsm.brain.model.po.BrainQuestionPO;
import com.hsm.brain.model.vo.question.QuestionQueryVO;
import com.hsm.brain.service.IBrainQuestionService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Classname BrainQuestionServiceImplTest
 * @Description TODO
 * @Date 2021/7/12 15:52
 * @Created by huangsm
 */
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class BrainQuestionServiceImplTest {

    @Autowired
    private IBrainQuestionService brainQuestionService;

    @Test
    public void add() {
        BrainQuestionPO brainQuestion = new BrainQuestionPO();
        brainQuestion.setCategoryId(1);
        brainQuestion.setContent("哪种水果视力最差?");
        brainQuestion.setAnswer("芒果");
        brainQuestion.setUserId(0);
        brainQuestion.setCtime(System.currentTimeMillis());
        brainQuestionService.add(brainQuestion);
    }

    @Test
    public void update() {
    }

    @Test
    public void selectById() {
    }

    @Test
    public void list() {
        QuestionQueryVO questionQueryVO = new QuestionQueryVO();
        questionQueryVO.setContent("豆腐");
        questionQueryVO.setPageNum(1);
        questionQueryVO.setPageSize(5);
        IPage<BrainQuestionPO> page = brainQuestionService.list(questionQueryVO);
        System.out.println(JSONValue.toJSONString(page));
    }
}