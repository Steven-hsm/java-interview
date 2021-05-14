package com.hsm.elasticsearch.service.impl;

import com.hsm.elasticsearch.entity.Main2;
import com.hsm.elasticsearch.entity.UserESPO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

import java.util.List;

/**
 * @Classname QueryBuilder常用方法
 * @Description TODO
 * @Date 2021/5/14 20:56
 * @Created by huangsm
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class QueryBuilder常用方法 {
    @Autowired
    private ElasticsearchTemplate<UserESPO, String> elasticsearchTemplate;

    @Test
    public void 精准查询() throws Exception {
        //精准查询的字段需要设置keyword属性（默认该属性为true），查询时fieldname需要带上.keyword
        QueryBuilder queryBuilder = QueryBuilders.termQuery("appli_name.keyword","456");
        List<UserESPO> list = elasticsearchTemplate.search(queryBuilder,UserESPO.class);
        list.forEach(main2 -> System.out.println(main2));
        //如果field类型直接为keyword可以不用加.keyword
    }

    @Test
    public void 短语查询(){

    }
}
