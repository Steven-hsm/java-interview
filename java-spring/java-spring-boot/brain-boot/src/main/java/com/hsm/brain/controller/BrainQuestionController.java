package com.hsm.brain.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hsm.brain.model.common.Result;
import com.hsm.brain.model.po.BrainQuestionPO;
import com.hsm.brain.model.po.CategoryPO;
import com.hsm.brain.model.vo.category.CategoryQueryVO;
import com.hsm.brain.model.vo.question.QuestionQueryVO;
import com.hsm.brain.service.IBrainQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author huangsm
 * @since 2021-07-10
 */
@RestController
@RequestMapping("/question")
public class BrainQuestionController {
    @Autowired
    private IBrainQuestionService brainQuestionService;

    @PostMapping("/add")
    private Result add(@RequestBody BrainQuestionPO brainQuestionPO) {
        brainQuestionService.add(brainQuestionPO);
        return Result.success();
    }

    @PostMapping("/update")
    private Result update(@RequestBody BrainQuestionPO brainQuestionPO) {
        brainQuestionService.update(brainQuestionPO);
        return Result.success();
    }

    @GetMapping("/detail/{questionId:\\d+}")
    private Result<BrainQuestionPO> detail(@PathVariable Integer questionId) {
        return Result.success(brainQuestionService.selectById(questionId));
    }

    @PostMapping("/list")
    private Result<IPage<BrainQuestionPO>> list(@RequestBody QuestionQueryVO questionQueryVO) {
        IPage<BrainQuestionPO> pageList = brainQuestionService.list(questionQueryVO);
        return Result.success(pageList);
    }
}
