package com.hsm.es;

import java.util.ArrayList;

import com.hsm.common.CollectionUtils;
import com.hsm.common.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedReverseNested;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.cardinality.ParsedCardinality;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;

/**
 * @Classname ESAggreate
 * @Description TODO
 * @Date 2021/9/13 10:30
 * @Created by huangsm
 */
@Slf4j
public class ESAggreate {
    RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("192.168.0.95", 9200, "http")));

    public static void main(String[] args) {
        /*StudentScoreSingleQo studentScoreSingleQo = new StudentScoreSingleQo();
        studentScoreSingleQo.setOrgId("001158921174800100456");

        new ESAggreate().groupByObject(studentScoreSingleQo,"student_score_single");*/
        ClassPromotionQueryVO classPromotionQuery = new ClassPromotionQueryVO();
        classPromotionQuery.setDateCode(0);
        classPromotionQuery.setGradeCode("");
        classPromotionQuery.setClassIdList(new ArrayList<String>());
        classPromotionQuery.setClassIds("");
        classPromotionQuery.setSubjectCode("");
        classPromotionQuery.setOrgId("");
        classPromotionQuery.setYearId("");

        new ESAggreate().countStudentGroupByKnpId(classPromotionQuery, null);


    }

    public void groupByObject(StudentScoreSingleQo qo, String indexPrefix) {
        /**
         * 01.构建查询条件
         */
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotEmpty(qo.getOrgId())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("orgId.keyword", qo.getOrgId()));
        }

        if (StringUtils.isNotEmpty(qo.getSubjectCode())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("subjectCode", qo.getSubjectCode()));
        }

        if (StringUtils.isNotEmpty(qo.getExamId())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("examId", qo.getExamId()));
        }

        if (StringUtils.isNotEmpty(qo.getClassId())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("classId", qo.getClassId()));
        }


        /**
         * 02.构建聚合
         */
        // 创建terms桶聚合，聚合名字=by_shcool, 字段=school，根据school分组
        // 等效于GROUP BY
        NestedAggregationBuilder nestedAggregationBuilder = AggregationBuilders.nested("agg", "kgScoreDetails");
        // 嵌套聚合
        // 设置count指标聚合，聚合名字=count_stuId, 字段=stuId，计算学生数
        // 等效于COUNT(stuId)
        nestedAggregationBuilder.subAggregation(
                AggregationBuilders.terms("groupByQuestionId").field("kgScoreDetails.questionId").subAggregation(
                        AggregationBuilders.reverseNested("reverseNestedAgg").subAggregation(// 一定要用reverseNested包裹，否则无法对nested字段上一层进行统计
                                AggregationBuilders.cardinality("count_studentId").field("studentId")
                        )
                ).size(Integer.MAX_VALUE)//  Sets the size - indicating how many term buckets should be returned
        );

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 分页参数
        searchSourceBuilder.from(0);
        // 如果只想返回聚合统计结果，不想返回查询结果可以将分页大小设置为0
        searchSourceBuilder.size(0);
        searchSourceBuilder.aggregation(nestedAggregationBuilder);
        searchSourceBuilder.query(boolQueryBuilder);

        /**
         * 03.构建查询请求
         */
        SearchRequest searchRequest = new SearchRequest(indexPrefix);
        searchRequest.types("_doc");
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info(response.toString());
            // 处理聚合查询结果
            Aggregations aggregations = response.getAggregations();

            //
            Nested agg = aggregations.get("agg");
            Terms groupByBrand = agg.getAggregations().get("groupByQuestionId");
            // 遍历terms聚合结果
            for (Terms.Bucket bucket : groupByBrand.getBuckets()) {
                // 因为是根据school分组，因此可以直接将桶的key转换成int类型
                String questionId = bucket.getKeyAsString();
                // 获取数量
                ParsedReverseNested parsedReverseNested = bucket.getAggregations().get("reverseNestedAgg");
                ParsedCardinality parsedCardinality = parsedReverseNested.getAggregations().get("count_studentId");
                log.info(questionId + ":" + parsedCardinality.getValue());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void countStudentGroupByKnpId(ClassPromotionQueryVO classPromotionQueryVO, List<String> examIdList) {


        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //班级id
        if (CollectionUtils.isNotEmpty(classPromotionQueryVO.getClassIdList())) {
            BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
            for (String classId : classPromotionQueryVO.getClassIdList()) {
                queryBuilder2.should(QueryBuilders.termQuery("classId.keyword", classId));
            }
            queryBuilder.must(queryBuilder2);
        }
        //试卷id数据
        if (CollectionUtils.isNotEmpty(examIdList)) {
            BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
            for (String examId : examIdList) {
                queryBuilder2.should(QueryBuilders.termQuery("examId.keyword", examId));
            }
            queryBuilder.must(queryBuilder2);
        }
        //考试科目
        if (StringUtils.isNotBlank(classPromotionQueryVO.getSubjectCode())) {
            queryBuilder.must(QueryBuilders.termQuery("subjectCode.keyword", classPromotionQueryVO.getSubjectCode()));
        }

        //仅保留得分率低用户信息
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("knowPointList.scoreRate");
        rangeQueryBuilder.lt(0.6);
        queryBuilder.must(QueryBuilders.nestedQuery("knowPointList", rangeQueryBuilder, ScoreMode.None));

        //
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(0);
        searchSourceBuilder.from(0);

        /**
         * 02.构建聚合
         */
        // 创建terms桶聚合，聚合名字=by_shcool, 字段=school，根据school分组
        // 等效于GROUP BY
        NestedAggregationBuilder aggregationBuilder = AggregationBuilders.nested("agg", "knowPointList");

        aggregationBuilder.subAggregation(
                AggregationBuilders.terms("groupByKnpId").field("knowPointList.knowPointId").subAggregation(
                        AggregationBuilders.reverseNested("reverseNestedAgg").subAggregation(// 一定要用reverseNested包裹，否则无法对nested字段上一层进行统计
                                AggregationBuilders.cardinality("count_studentId").field("studentId.keyword")
                        )
                ).size(20)
        );

        searchSourceBuilder.aggregation(aggregationBuilder);
        SearchRequest searchRequest = new SearchRequest("student_score_single_v2"); // 新建索引搜索请求
        searchRequest.source(searchSourceBuilder);

        /**
         * 03.构建查询请求
         */
        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            //log.info(response.toString());
            // 处理聚合查询结果
            Aggregations aggregations = response.getAggregations();

            //
            Nested agg = aggregations.get("agg");
            Terms groupByKnpId = agg.getAggregations().get("groupByKnpId");
            // 遍历terms聚合结果
            for (Terms.Bucket bucket : groupByKnpId.getBuckets()) {
                // 因为是根据school分组，因此可以直接将桶的key转换成int类型
                String knpId = bucket.getKeyAsString();
                // 获取数量
                ParsedReverseNested parsedReverseNested = bucket.getAggregations().get("reverseNestedAgg");
                ParsedCardinality parsedCardinality = parsedReverseNested.getAggregations().get("count_studentId");
                log.info(knpId + ":" + parsedCardinality.getValue());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
