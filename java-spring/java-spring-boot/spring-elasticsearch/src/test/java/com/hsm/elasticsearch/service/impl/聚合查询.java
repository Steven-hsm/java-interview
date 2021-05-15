package com.hsm.elasticsearch.service.impl;

import com.hsm.elasticsearch.entity.UserESPO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.aggregations.metrics.Stats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zxp.esclientrhl.enums.AggsType;
import org.zxp.esclientrhl.repository.Down;
import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

import java.util.List;
import java.util.Map;

/**
 * @Classname 聚合查询
 * @Description TODO
 * @Date 2021/5/15 15:43
 * @Created by huangsm
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class 聚合查询 {
    @Autowired
    private ElasticsearchTemplate<UserESPO, String> elasticsearchTemplate;

    @Test
    public void 普通聚合查询() throws Exception{
        double sum = elasticsearchTemplate.aggs("age", AggsType.sum,null,UserESPO.class);
        double count = elasticsearchTemplate.aggs("age", AggsType.count,null,UserESPO.class);
        double avg = elasticsearchTemplate.aggs("age", AggsType.avg,null,UserESPO.class);
        double min = elasticsearchTemplate.aggs("age", AggsType.min,null,UserESPO.class);
        //如果翻译成sql：select max(sum_premium) from Main2
        double max = elasticsearchTemplate.aggs("age", AggsType.max,null,UserESPO.class);

        System.out.println("sum===="+sum);
        System.out.println("count===="+count);
        System.out.println("avg===="+avg);
        System.out.println("min===="+min);
        System.out.println("max===="+max);
    }

    @Test
    public void 分组普通聚合查询() throws Exception{
        //如果翻译成sql：select appli_name,max(sum_premium) from Main2 group by appli_name
        Map map = elasticsearchTemplate.aggs("age", AggsType.sum,null,UserESPO.class,"userName");
        map.forEach((k,v) -> System.out.println(k+"     "+v));
    }

    @Test
    public void 下钻2层聚合查询() throws Exception{
        //select appli_name,risk_code,sum(sumpremium) from Main2 group by appli_name,risk_code
        List<Down> list = elasticsearchTemplate.aggswith2level("age", AggsType.sum,null,UserESPO.class,new String[]{"userName","userCode"});
        list.forEach(down ->
                {
                    System.out.println("1:"+down.getLevel_1_key());
                    System.out.println("2:"+down.getLevel_2_key() + "    "+ down.getValue());
                }
        );
    }

    @Test
    public void 统计聚合查询() throws Exception{
        //此方法可以一次查询便返回针对metricName统计分析的sum、count、avg、min、max指标值
        Stats stats = elasticsearchTemplate.statsAggs("age",null,UserESPO.class);
        System.out.println("max:"+stats.getMax());
        System.out.println("min:"+stats.getMin());
        System.out.println("sum:"+stats.getSum());
        System.out.println("count:"+stats.getCount());
        System.out.println("avg:"+stats.getAvg());
    }

    @Test
    public void 分组统计聚合查询() throws Exception{
        Map<String,Stats> stats = elasticsearchTemplate.statsAggs("age",null,UserESPO.class,"userName");
        stats.forEach((k,v) ->
                {
                    System.out.println(k+"    count:"+v.getCount()+" sum:"+v.getSum()+"...");
                }
        );
    }

    @Test
    public void 基数查询() throws Exception{
        //select count(distinct proposal_no) from Main2
        //基数查询，即count(distinct)返回一个近似值，并不一定会非常准确
        long value = elasticsearchTemplate.cardinality("age",null,UserESPO.class);
        System.out.println(value);
        //long value = elasticsearchTemplate.cardinality("proposal_no",null,3000L,UserESPO.class);
    }

}
