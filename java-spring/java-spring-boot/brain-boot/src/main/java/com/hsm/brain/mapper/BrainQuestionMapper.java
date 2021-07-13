package com.hsm.brain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsm.brain.model.po.BrainQuestionPO;
import com.hsm.brain.model.po.CategoryPO;
import com.hsm.brain.model.vo.question.QuestionQueryVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangsm
 * @since 2021-07-10
 */
public interface BrainQuestionMapper extends BaseMapper<BrainQuestionPO> {
    /**
     * 分页查询数据
     * @param page
     * @param questionQueryVO
     * @return
     */
    IPage<BrainQuestionPO> listWithPage(Page<CategoryPO> page, @Param("vo") QuestionQueryVO questionQueryVO);

    /**
     * 下一题
     * @param questionId
     * @return
     */
    BrainQuestionPO next(@Param("questionId") Integer questionId);

    /**
     * 上一题
     * @param questionId
     * @return
     */
    BrainQuestionPO previous(@Param("questionId") Integer questionId);
}
