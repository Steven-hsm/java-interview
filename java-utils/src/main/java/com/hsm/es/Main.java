package com.hsm.es;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Classname Main
 * @Description TODO
 * @Date 2021/7/8 11:03
 * @Created by huangsm
 */
public class Main {
    public static void main(String[] args) {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.0.81", 9200, "http")));

        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        SearchRequest searchRequest = new SearchRequest("student_score_single"); // 新建索引搜索请求
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(5000); //设定每次返回多少条数据
        //searchSourceBuilder.fetchSource(new String[]{"classGroupId"},null);//设置返回字段和排除字段
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int page = 0 ;
        File outFile = new File("E://cater_nid.csv");//写出的CSV文件
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));

            SearchHit[] searchHits = searchResponse.getHits().getHits();
            page++;
            System.out.println("-----第"+ page +"页-----");
            for (SearchHit searchHit : searchHits) {
                System.out.println(searchHit.getSourceAsString());
                String sourceAsString = searchHit.getSourceAsString();
                StudentScoreSingleDTO t = JSON.parseObject(sourceAsString, StudentScoreSingleDTO.class);
                writer.write(sourceAsString);
                writer.newLine();
            }

            //遍历搜索命中的数据，直到没有数据
            String scrollId = searchResponse.getScrollId();
            while (searchHits != null && searchHits.length > 0) {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                try {
                    searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                scrollId = searchResponse.getScrollId();
                searchHits = searchResponse.getHits().getHits();
                if (searchHits != null && searchHits.length > 0) {
                    page++;
                    System.out.println("-----第"+ page +"页-----");
                    for (SearchHit searchHit : searchHits) {
                        System.out.println(searchHit.getSourceAsString());
                        String sourceAsString = searchHit.getSourceAsString();
                        StudentScoreSingleDTO t = JSON.parseObject(sourceAsString, StudentScoreSingleDTO.class);
                        writer.write(sourceAsString);
                        writer.newLine();
                    }
                }
            }
            //清除滚屏
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);//也可以选择setScrollIds()将多个scrollId一起使用
            ClearScrollResponse clearScrollResponse = null;
            try {
                clearScrollResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            boolean succeeded = clearScrollResponse.isSucceeded();
            System.out.println("succeeded:" + succeeded);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
