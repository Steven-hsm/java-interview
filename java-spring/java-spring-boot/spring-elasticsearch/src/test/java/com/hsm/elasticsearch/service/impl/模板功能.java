package com.hsm.elasticsearch.service.impl;

import com.hsm.elasticsearch.entity.Main2;
import com.hsm.elasticsearch.entity.UserESPO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname 模板功能
 * @Description TODO
 * @Date 2021/5/14 20:39
 * @Created by huangsm
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class 模板功能 {
    @Autowired
    private ElasticsearchTemplate<Main2, String> elasticsearchTemplate;

    @Test
    public void 保存模板() throws Exception {
        String templatesource = "{\n" +
                "  \"script\": {\n" +
                "    \"lang\": \"mustache\",\n" +
                "    \"source\": {\n" +
                "      \"_source\": [\n" +
                "        \"proposal_no\",\"appli_name\"\n" +
                "      ],\n" +
                "      \"size\": 20,\n" +
                "      \"query\": {\n" +
                "        \"term\": {\n" +
                "          \"appli_name\": \"{{name}}\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        elasticsearchTemplate.saveTemplate("tempdemo1",templatesource);
    }

    @Test
    public void 注册模板() throws Exception {
        Map param = new HashMap();
        param.put("name","123");
        elasticsearchTemplate.searchTemplate(param,"tempdemo1", Main2.class).forEach(s -> System.out.println(s));
    }

    @Test
    public void 内联模板() throws Exception {
        Map param = new HashMap();
        param.put("name","123");
        String templatesource = "{\n" +
                "      \"query\": {\n" +
                "        \"term\": {\n" +
                "          \"appli_name\": \"{{name}}\"\n" +
                "        }\n" +
                "      }\n" +
                "}";
        elasticsearchTemplate.searchTemplateBySource(param,templatesource,Main2.class).forEach(s -> System.out.println(s));
    }
}
