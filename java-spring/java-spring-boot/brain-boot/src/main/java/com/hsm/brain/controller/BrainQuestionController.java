package com.hsm.brain.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hsm.brain.model.common.Result;
import com.hsm.brain.model.po.BrainQuestionPO;
import com.hsm.brain.model.vo.question.QuestionQueryVO;
import com.hsm.brain.service.IBrainQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "试题模块")
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
    @ApiOperation(value = "试题详情查询")
    private Result<BrainQuestionPO> detail(@PathVariable Integer questionId) {
        return Result.success(brainQuestionService.selectById(questionId));
    }

    @PostMapping("/list")
    @ApiOperation(value = "试题列表查询")
    private Result<IPage<BrainQuestionPO>> list(@RequestBody QuestionQueryVO questionQueryVO) {
        IPage<BrainQuestionPO> pageList = brainQuestionService.list(questionQueryVO);
        return Result.success(pageList);
    }

    @GetMapping("/previous/{questionId:\\d+}")
    @ApiOperation(value = "上一题")
    private Result<BrainQuestionPO> previous(@PathVariable Integer questionId) {
        return Result.success(brainQuestionService.previous(questionId));
    }


    @GetMapping("/next/{questionId:\\d+}")
    @ApiOperation(value = "下一题")
    private Result<BrainQuestionPO> next(@PathVariable Integer questionId) {
        return Result.success(brainQuestionService.next(questionId));
    }

}
