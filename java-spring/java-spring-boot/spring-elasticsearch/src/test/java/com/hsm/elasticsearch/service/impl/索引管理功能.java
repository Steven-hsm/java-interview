package com.hsm.elasticsearch.service.impl;

import com.hsm.elasticsearch.entity.UserESPO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zxp.esclientrhl.index.ElasticsearchIndex;

/**
 * @Classname 索引管理功能
 * @Description 索引管理功能
 * @Date 2021/5/13 18:16
 * @Created by huangsm
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class 索引管理功能 {

    @Autowired
    private ElasticsearchIndex elasticsearchIndex;

    @Test
    public void 删除索引() throws Exception{
        elasticsearchIndex.dropIndex(UserESPO.class);
    }
}
