package com.hsm.elasticsearch.service.impl;

import com.hsm.elasticsearch.entity.Main2;
import com.hsm.elasticsearch.entity.UserESPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.*;
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
        QueryBuilder queryBuilder = QueryBuilders.termQuery("userName.keyword", "王五");
        List<UserESPO> list = elasticsearchTemplate.search(queryBuilder, UserESPO.class);
        list.forEach(user -> System.out.println(user));
        //如果field类型直接为keyword可以不用加.keyword
    }

    @Test
    public void 短语查询() throws Exception {
        //必须相邻的查询条件
        QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("userName", "王1");
        List<UserESPO> list = elasticsearchTemplate.search(queryBuilder, UserESPO.class);
        list.forEach(user -> System.out.println(user));
    }

    @Test
    public void 相关度查询() throws Exception {
        //slop设置为2，最多能移动两次并完成匹配
        QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("userName", "王1").slop(2);
        List<UserESPO> list = elasticsearchTemplate.search(queryBuilder, UserESPO.class);
        list.forEach(user -> System.out.println(user));
    }

    @Test
    public void 范围查询() throws Exception {
        QueryBuilder queryBuilder = QueryBuilders.rangeQuery("age").from(10).to(16);
        List<UserESPO> list = elasticsearchTemplate.search(queryBuilder, UserESPO.class);
        list.forEach(user -> System.out.println(user));
    }

    @Test
    public void 全文匹配() throws Exception {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("userName", "王五1");
        List<UserESPO> list = elasticsearchTemplate.search(queryBuilder, UserESPO.class);
        list.forEach(user -> System.out.println(user));
    }

    @Test
    public void minimumShouldMatch最少匹配参数() throws Exception {
        //最少匹配词语数量是75%，该查询查不到信息
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("userName", "王五").minimumShouldMatch("1%");
        List<UserESPO> list = elasticsearchTemplate.search(queryBuilder, UserESPO.class);
        list.forEach(user -> System.out.println(user));
    }

    @Test
    public void match查询集成fuzzy纠错查询() throws Exception {
        //最少匹配词语数量是75%，该查询查不到信息
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("userName", "王五").minimumShouldMatch("75%");
        List<UserESPO> list = elasticsearchTemplate.search(queryBuilder, UserESPO.class);
        list.forEach(user -> System.out.println(user));
    }

    @Test
    public void match查询设定查询条件逻辑关系() throws Exception {
        //默认是OR
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("userName", "王五").operator(Operator.AND);
        List<UserESPO> list = elasticsearchTemplate.search(queryBuilder, UserESPO.class);
        list.forEach(user -> System.out.println(user));
    }

    @Test
    public void fuzzy纠错查询() throws Exception {
        //原文是spring，查询条件输入为spting也能查询到结果
        QueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("userName", "王");
        List<UserESPO> list = elasticsearchTemplate.search(queryBuilder, UserESPO.class);
        list.forEach(user -> System.out.println(user));
    }

    @Test
    public void boost权重设置() throws Exception {
        //查询结果appli_name为spring的会被优先展示其次456，再次123
        QueryBuilder queryBuilder1 = QueryBuilders.termQuery("userName.keyword", "spring").boost(5);
        QueryBuilder queryBuilder2 = QueryBuilders.termQuery("userName.keyword", "456").boost(3);
        QueryBuilder queryBuilder3 = QueryBuilders.termQuery("userName.keyword", "123");
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.should(queryBuilder1).should(queryBuilder2).should(queryBuilder3);
    }

    @Test
    public void prefix前缀查询() throws Exception {
        //查询结果appli_name为spring的会被优先展示其次456，再次123
        QueryBuilder queryBuilder = QueryBuilders.prefixQuery("userName", "王");
        List<UserESPO> list = elasticsearchTemplate.search(queryBuilder, UserESPO.class);
        list.forEach(user -> System.out.println(user));
    }

    @Test
    public void wildcard通配符查询() throws Exception {
        //性能较差不建议使用
        //?：任意字符
        //*：0个或任意多个字符
        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("userName","王?1");
        List<UserESPO> list = elasticsearchTemplate.search(queryBuilder, UserESPO.class);
        list.forEach(user -> System.out.println(user));
    }

    @Test
    public void regexp正则查询() throws Exception {
        //性能较差不建议使用
        QueryBuilder queryBuilder = QueryBuilders.regexpQuery("userName","王.+");
        List<UserESPO> list = elasticsearchTemplate.search(queryBuilder, UserESPO.class);
        list.forEach(user -> System.out.println(user));
    }

    @Test
    public void 组合逻辑查询() throws Exception {
        //select * from Main2 where (appli_name = 'spring' or appli_name = '456') and risk_code = '0101' and proposal_no != '1234567'
        QueryBuilder queryBuilder1 = QueryBuilders.termQuery("userName.keyword","王五");
        QueryBuilder queryBuilder2 = QueryBuilders.termQuery("userName.keyword","王五");
        QueryBuilder queryBuilder3 = QueryBuilders.termQuery("age",25);
        QueryBuilder queryBuilder4 = QueryBuilders.termQuery("userName.keyword","王五");
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.should(queryBuilder1).should(queryBuilder2);
        queryBuilder.must(queryBuilder3);
        queryBuilder.mustNot(queryBuilder4);
    }

    @Test
    public void disMaxQuery() throws Exception {
        //所有查询条件中只取匹配度最高的那个条件的分数作为最终分数
        //假设
        //第一条数据title匹配bryant fox的分数为0.8 body匹配bryant fox的分数为0.1，这条数据最终得分为0.8
        //第二条数据title匹配bryant fox的分数为0.6 body匹配bryant fox的分数为0.7，这条数据最终得分为0.7
        //dis_max查询后第一条数据相关度评分更高，排在第二条数据的前面
        //如果不用dis_max，则第二条数据的得分为1.4高于第一条数据的0.9
        //如果再附加其他匹配结果的分数，需要指定tieBreaker
        //获得最佳匹配语句句的评分_score
        //将其他匹配语句句的评分与tie_breaker 相乘
        //对以上评分求和并规范化
        //Tier Breaker：介于0-1 之间的浮点数（0代表使⽤用最佳匹配；1 代表所有语句句同等重要）
        QueryBuilders.disMaxQuery()
                .add(QueryBuilders.matchQuery("title", "bryant fox"))
                .add(QueryBuilders.matchQuery("body", "bryant fox"))
                .tieBreaker(0.2f);
    }

    @Test
    public void multiMatchQuery() throws Exception {
        //最佳匹配best_fields：和Dis Max Query效果一样
        QueryBuilders.multiMatchQuery("Quick pets", "title","body")
                .minimumShouldMatch("20%")
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                .tieBreaker(0.2f);
        //最多匹配most_fields：能匹配到更多字段的记录优先（不管其中某一个字段有多么匹配）
        QueryBuilders.multiMatchQuery("shanxi datong", "s1","s2","s3","s4")
                .type(MultiMatchQueryBuilder.Type.MOST_FIELDS);
        //跨字段匹配cross_fields：综合起来最匹配的，即类似将所有字段合并到一个字段中，搜索这个字段看谁分高 最佳实践：可以代替copy_to节省了一个字段的倒排空间
    }

    @Test
    public void boostingQuery() throws Exception {
        BoostingQueryBuilder boostingQueryBuilder = QueryBuilders.boostingQuery(QueryBuilders.matchQuery("userName", "王五"),
                QueryBuilders.matchQuery("age", "25")).negativeBoost(0.2f);
        List<UserESPO> list = elasticsearchTemplate.search(boostingQueryBuilder, UserESPO.class);
        list.forEach(user -> System.out.println(user));
    }

    @Test
    public void 过滤器() throws Exception {
        //select * from Main2 where appli_name = '456' and risk_code = '0101'
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("userName.keyword","王五"))
        //下面如果会比较频繁的作为子集合，就比较适合通过filter来缓存
                .filter(QueryBuilders.matchPhraseQuery("age",25));
        List<UserESPO> list = elasticsearchTemplate.search(queryBuilder,UserESPO.class);
        list.forEach(user -> System.out.println(user));
    }


    @Test
    public void nested查询() throws Exception {
        NestedQueryBuilder queryBuilder = QueryBuilders.nestedQuery("chinaName",QueryBuilders.matchQuery("chinaName.firstName","张"), ScoreMode.Total);
        elasticsearchTemplate.search(queryBuilder, UserESPO.class).forEach(s -> {System.out.println(s);});
    }
}
