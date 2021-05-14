package com.hsm.elasticsearch.service.impl;

import com.hsm.elasticsearch.entity.ChinaNamePO;
import com.hsm.elasticsearch.entity.UserESPO;
import com.hsm.elasticsearch.service.IReposityServer;
import com.hsm.elasticsearch.service.UserReposity;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zxp.esclientrhl.enums.SqlFormat;
import org.zxp.esclientrhl.repository.*;
import org.zxp.esclientrhl.repository.response.ScrollResponse;
import org.zxp.esclientrhl.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Classname CRUD
 * @Description TODO
 * @Date 2021/5/13 17:44
 * @Created by huangsm
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CRUD功能说明 {
    @Autowired
    private ElasticsearchTemplate<UserESPO, String> elasticsearchTemplate;
    @Autowired
    private IReposityServer reposityServer;
    @Autowired
    private UserReposity userReposity;

    @Test
    public void 新增索引数据() {
        UserESPO userES = new UserESPO();
        ChinaNamePO chinaName = new ChinaNamePO();
        chinaName.setFirstName("张");
        chinaName.setLastName("三丰");

        userES.setUserCode(UUID.randomUUID().toString());
        userES.setAge(15);
        userES.setUserName("张三131");
        userES.setChinaName(chinaName);
        try {
            //这里可以指定分片
            elasticsearchTemplate.save(userES);
        } catch (Exception e) {
            log.error("保存用户数据异常", e);
        }
    }

    @Test
    public void 批量新增索引() {
        List<UserESPO> userList = new ArrayList<>();
        userList.add(new UserESPO(UUID.randomUUID().toString(), "王五1", 25));
        userList.add(new UserESPO(UUID.randomUUID().toString(), "王五2", 25));
        userList.add(new UserESPO(UUID.randomUUID().toString(), "王五3", 25));
        userList.add(new UserESPO(UUID.randomUUID().toString(), "王五4", 25));

        try {
            elasticsearchTemplate.save(userList);
        } catch (Exception e) {
            log.error("保存用户数据异常", e);
        }
    }

    @Test
    public void 分批次新增索引() {
        List<UserESPO> userList = new ArrayList<>();
        userList.add(new UserESPO(UUID.randomUUID().toString(), "王五1", 25));
        userList.add(new UserESPO(UUID.randomUUID().toString(), "王五2", 25));
        userList.add(new UserESPO(UUID.randomUUID().toString(), "王五3", 25));
        userList.add(new UserESPO(UUID.randomUUID().toString(), "王五4", 25));

        try {
            //相比于批量新增索引数据，分批次新增索引数据考虑了es服务端批量索引数据的内存瓶颈，
            // 将根据一些简单的策略对传入的数据列表进行拆分并顺序索引
            elasticsearchTemplate.saveBatch(userList);
        } catch (Exception e) {
            log.error("保存用户数据异常", e);
        }
    }

    @Test
    public void 部分更新索引数据() {
        UserESPO userESPO = new UserESPO();
        userESPO.setUserCode("55a51764-ffa9-4011-893d-ba9f5bdfc077");
        userESPO.setUserName("部分更新之后的名字");
        try {
            //部分更新值更新设置了值的字段
            //nested对象更新必须使用覆盖更新
            elasticsearchTemplate.update(userESPO);
        } catch (Exception e) {
            log.error("保存用户数据异常", e);
        }
    }

    @Test
    public void 覆盖更新索引数据() {
        UserESPO userESPO = new UserESPO();
        userESPO.setUserCode("55a51764-ffa9-4011-893d-ba9f5bdfc077");
        userESPO.setUserName("覆盖更新之后的名字");
        try {
            //部分更新值更新设置了值的字段
            //nested对象更新必须使用覆盖更新
            elasticsearchTemplate.updateCover(userESPO);
        } catch (Exception e) {
            log.error("保存用户数据异常", e);
        }
    }

    @Test
    public void 批量更新索引() {
        UserESPO userESPO = new UserESPO();
        userESPO.setChinaName(new ChinaNamePO("批量", "更新"));
        try {
            //根据QueryBuilders查询的结果进行更新
            //批量更新索引不支持覆盖更新,这里会报错
            elasticsearchTemplate.batchUpdate(QueryBuilders.matchQuery("userName", "王五1"), userESPO,
                    UserESPO.class, 30, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void 删除数据() {
        UserESPO userESPO = new UserESPO();

        try {
            //1. 通过指定id删除数据
            elasticsearchTemplate.deleteById("e6889124-ee97-472d-95bd-f80b5dd5e1a1", UserESPO.class);

            //2. 通过对象删除
            elasticsearchTemplate.delete(userESPO);

            //3. 根据查询条件删除索引数据
            elasticsearchTemplate.deleteByCondition(QueryBuilders.matchQuery("userName", "王五3"), UserESPO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void 判断索引是否存在() {
        try {
            elasticsearchTemplate.exists("55a51764-ffa9-4011-893d-ba9f5bdfc077", UserESPO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void 原生查询() {
        SearchRequest searchRequest = new SearchRequest(new String[]{"user"});
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(new MatchAllQueryBuilder());
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = elasticsearchTemplate.search(searchRequest);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                UserESPO t = JsonUtils.string2Obj(hit.getSourceAsString(), UserESPO.class);
                System.out.println(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void uri查询() throws InterruptedException {
        List<UserESPO> userList = null;
        List<UserESPO> userList2 = null;
        try {
            //
            userList = elasticsearchTemplate.searchUri("q=25", UserESPO.class);
            //
            userList2 = elasticsearchTemplate.searchUri("q=age:25", UserESPO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("查询结果：{}", JsonUtils.obj2String(userList));
        log.info("查询结果：{}", JsonUtils.obj2String(userList2));
    }

    @Test
    public void sql查询() throws Exception {
        //全查询
        String result = elasticsearchTemplate.queryBySQL("SELECT * FROM user ORDER BY age DESC LIMIT 5", SqlFormat.TXT);
        log.info("查询结果：{}", JsonUtils.obj2String(result));
        //查询count
        String result2 = elasticsearchTemplate.queryBySQL("SELECT count(*) FROM user ", SqlFormat.TXT);
        log.info("查询结果：{}", JsonUtils.obj2String(result2));
        //分组查询
        String result3 = elasticsearchTemplate.queryBySQL("SELECT userName,age FROM user", SqlFormat.TXT);
        log.info("查询结果：{}", JsonUtils.obj2String(result3));
    }

    @Test
    public void 支持查询条件查询() throws Exception {
        //这里简单通过matchall（全查询）的方式进行演示
        //QueryBuilder的用法会在单独章节介绍
        List<UserESPO> userList = elasticsearchTemplate.search(new MatchAllQueryBuilder(), UserESPO.class);
        userList.forEach(user -> System.out.println(user));

       /* List<UserESPO> userList2 = userReposity.searchMore(new MatchAllQueryBuilder(),7);
        userList2.forEach(user -> System.out.println(user));*/
    }

    @Test
    public void 支持分页高亮查询() throws Exception {
        //定制分页信息
        int currentPage = 1;
        int pageSize = 10;

        //分页
        PageSortHighLight psh = new PageSortHighLight(currentPage, pageSize);
        //排序字段，注意如果proposal_no是text类型会默认带有keyword性质，需要拼接.keyword
        String sorter = "userCode.keyword";
        Sort.Order order = new Sort.Order(SortOrder.ASC, sorter);
        psh.setSort(new Sort(order));
        //定制高亮，如果定制了高亮，返回结果会自动替换字段值为高亮内容
        psh.setHighLight(new HighLight().field("userCode"));

        //可以单独定义高亮的格式
        //new HighLight().setPreTag("<em>");
        //new HighLight().setPostTag("</em>");
        PageList<UserESPO> pageList = elasticsearchTemplate.search(new MatchAllQueryBuilder(), psh, UserESPO.class);
        pageList.getList().forEach(main2 -> System.out.println(main2));
    }

    @Test
    public void 高级查询() throws Exception {
        PageSortHighLight psh = new PageSortHighLight(1, 50);
        Attach attach = new Attach();
        attach.setPageSortHighLight(psh);
        PageList<UserESPO> search = elasticsearchTemplate.search(QueryBuilders.matchQuery("userName", "王五2"), attach, UserESPO.class);
        search.getList().forEach(user -> System.out.println(user));

        //指定返回字段
        Attach attach2 = new Attach();
        //返回结果只包含proposal_no字段
        String[] includes = {"age"};
        attach.setIncludes(includes);
        PageList<UserESPO> userCode = elasticsearchTemplate.search(QueryBuilders.termQuery("userCode", "e6889124-ee97-472d-95bd-f80b5dd5e1a1"), attach, UserESPO.class);
        userCode.getList().forEach(user -> System.out.println(user));
    }

    @Test
    public void count查询() throws Exception {
        long count = elasticsearchTemplate.count(new MatchAllQueryBuilder(), UserESPO.class);
        System.out.println(count);
    }

    @Test
    public void scroll查询() throws Exception {
        //创建scroll并获得第一批数据
        ScrollResponse<UserESPO> scrollResponse = elasticsearchTemplate.createScroll(new MatchAllQueryBuilder(), UserESPO.class, 1L, 2);
        scrollResponse.getList().forEach(s -> System.out.println(s));
        String scrollId = scrollResponse.getScrollId();
        //通过scrollId获取其他批次的数据
        while (true) {
            scrollResponse = elasticsearchTemplate.queryScroll(UserESPO.class, 1L, scrollId);
            if (scrollResponse.getList() != null && scrollResponse.getList().size() != 0) {
                scrollResponse.getList().forEach(s -> System.out.println(s));
                scrollId = scrollResponse.getScrollId();
            } else {
                break;
            }
        }
    }

    @Test
    public void 根据id查询() throws Exception {
        UserESPO user = elasticsearchTemplate.getById("e6889124-ee97-472d-95bd-f80b5dd5e1a1", UserESPO.class);
        System.out.println(user);
    }

    @Test
    public void mget() throws Exception {
        String[] list = {"e6889124-ee97-472d-95bd-f80b5dd5e1a1"};
        List<UserESPO> listResult = elasticsearchTemplate.mgetById(list, UserESPO.class);
        listResult.forEach(main -> System.out.println(main));
    }
}