package com.hsm.elasticsearch.service.impl;

import com.hsm.elasticsearch.entity.UserESPO;
import com.hsm.elasticsearch.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zxp.esclientrhl.index.ElasticsearchIndex;
import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

@Service
@Slf4j
public class UserService implements IUserService {
    @Autowired
    ElasticsearchTemplate<UserESPO,String> elasticsearchTemplate;

    @Autowired
    ElasticsearchIndex<UserESPO> userIndex;
    @Override
    public boolean add(UserESPO user) {
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
            userIndex.dropIndex(UserESPO.class);
        } catch (Exception e) {
            log.error("删除索引异常");
        }
    }

    @Override
    public void createIndex() {
        try {
            userIndex.createIndex(UserESPO.class);
        } catch (Exception e) {
            log.error("创建索引异常");
        }
    }
}
