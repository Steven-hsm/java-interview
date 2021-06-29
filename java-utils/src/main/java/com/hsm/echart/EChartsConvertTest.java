package com.hsm.echart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hsm.base64.ImageUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname EChartsConvertTest
 * @Description TODO
 * @Date 2021/6/29 17:48
 * @Created by huangsm
 */
public class EChartsConvertTest {
    public static void main(String[] args) {
        String url = "http://192.168.0.164:9090";
        // 不必要的空格最好删除，字符串请求过程中会将空格转码成+号
        String optJson = "{title:{text:'ECharts 示例'},tooltip:{},legend:{data:['销量']},"
                + "xAxis:{data:['衬衫','羊毛衫','雪纺衫','裤子','高跟鞋','袜子']},yAxis:{},"
                + "series:[{name:'销量',type:'bar',data:[5,20,36,10,10,20]}]}";
        Map<String, String> map = new HashMap<>();
        map.put("opt", optJson);
        try {
            String post = post(url, map, "utf-8");
            JSONObject jsonObject = JSON.parseObject(post);
            String data = jsonObject.getString("data");
            ImageUtil.base64StringToImage(data,"1.png");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    // post请求
    public static String post(String url, Map<String, String> map, String encoding) throws ParseException, IOException {
        String body = "";

        // 创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 装填参数
        List<NameValuePair> nvps = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        // 设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        // 获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        // 释放链接
        response.close();
        return body;
    }
}

