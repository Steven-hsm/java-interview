package com.hsm.brain.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsm.brain.Exception.ServiceException;
import com.hsm.brain.mapper.BrainQuestionMapper;
import com.hsm.brain.model.po.BrainQuestionPO;
import com.hsm.brain.model.po.CategoryPO;
import com.hsm.brain.model.vo.question.QuestionQueryVO;
import com.hsm.brain.service.IBrainQuestionService;
import com.hsm.brain.utils.SqlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author huangsm
 * @since 2021-07-10
 */
@Service
public class BrainQuestionServiceImpl extends ServiceImpl<BrainQuestionMapper, BrainQuestionPO> implements IBrainQuestionService {
    @Autowired
    private BrainQuestionMapper brainQuestionMapper;

    @Override
    public void add(BrainQuestionPO brainQuestionPO) {
        brainQuestionPO.setUserId(0);
        brainQuestionPO.setCtime(System.currentTimeMillis());
        brainQuestionMapper.insert(brainQuestionPO);
    }

    @Override
    public void update(BrainQuestionPO brainQuestionPO) {
        BrainQuestionPO oldBrainQuestion = this.selectById(brainQuestionPO.getId());
        if (oldBrainQuestion == null) {
            throw new ServiceException("无法获取原数据，更新异常");
        }
        BeanUtils.copyProperties(oldBrainQuestion, brainQuestionPO);
        brainQuestionMapper.updateById(brainQuestionPO);
    }

    @Override
    public BrainQuestionPO selectById(Integer questionId) {
        return brainQuestionMapper.selectById(questionId);
    }

    @Override
    public IPage<BrainQuestionPO> list(QuestionQueryVO questionQueryVO) {
        Page<CategoryPO> page = new Page<>(questionQueryVO.getPageNum(), questionQueryVO.getPageSize());
        //将名称设置为模糊查询
        questionQueryVO.setContent(SqlUtils.contactLike(questionQueryVO.getContent()));
        return brainQuestionMapper.listWithPage(page, questionQueryVO);
    }

    @Override
    public BrainQuestionPO next(Integer questionId) {
        return brainQuestionMapper.next(questionId);
    }

    @Override
    public BrainQuestionPO previous(Integer questionId) {
        return brainQuestionMapper.previous(questionId);
    }
}
