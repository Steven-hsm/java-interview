package com.hsm.elasticsearch.service.impl;

import com.hsm.elasticsearch.entity.UserPO;
import com.hsm.elasticsearch.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zxp.esclientrhl.index.ElasticsearchIndex;
import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Autowired
    ElasticsearchTemplate<UserPO,String> elasticsearchTemplate;

    @Autowired
    ElasticsearchIndex<UserPO> userIndex;
    @Override
    public boolean add(UserPO user) {
        boolean result = false;
        try {
            result = elasticsearchTemplate.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void dropIndex() {
        try {
            userIndex.dropIndex(UserPO.class);
        } catch (Exception e) {
            log.error("删除索引异常");
        }
    }

    @Override
    public void createIndex() {
        try {
            userIndex.createIndex(UserPO.class);
        } catch (Exception e) {
            log.error("创建索引异常");
        }
    }
}
