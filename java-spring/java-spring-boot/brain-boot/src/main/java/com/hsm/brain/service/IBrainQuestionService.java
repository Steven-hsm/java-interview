package com.hsm.brain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hsm.brain.model.po.BrainQuestionPO;
import com.hsm.brain.model.vo.question.QuestionQueryVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangsm
 * @since 2021-07-10
 */
public interface IBrainQuestionService extends IService<BrainQuestionPO> {

    /**
     * 添加试题
     * @param brainQuestionPO
     */
    void add(BrainQuestionPO brainQuestionPO);

    /**
     * 更新试题
     * @param brainQuestionPO
     */
    void update(BrainQuestionPO brainQuestionPO);

    /**
     * 获取试题详情数据
     * @param questionId
     * @return
     */
    BrainQuestionPO selectById(Integer questionId);

    /**
     * 分页查询试题数据
     * @param questionQueryVO
     * @return
     */
    IPage<BrainQuestionPO> list(QuestionQueryVO questionQueryVO);

    /**
     * 下一题
     * @param questionId
     * @return
     */
    BrainQuestionPO next(Integer questionId);

    /**
     * 上一题
     * @param questionId
     * @return
     */
    BrainQuestionPO previous(Integer questionId);
}
