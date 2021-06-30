package com.hsm.transposition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Classname TransUtils
 * @Description TODO
 * @Date 2021/6/30 9:55
 * @Created by huangsm
 */
public class TransUtils {

    /**
     * 将对象列表转置为二位数组
     * @param dataList 对象列表
     * @param fieldList 转置的字段列表
     * @return
     */
    public static String[][] convertToArray(List dataList,List<String> fieldList){
        JSONArray jsonArray = JSONObject.parseArray(JSON.toJSONString(dataList));
        String[][] strArray = new String[fieldList.size()][jsonArray.size()];
        for (int i = 0; i < fieldList.size(); i++) {
            for (int j = 0; j < jsonArray.size(); j++) {
                strArray[i][j] = String.valueOf(jsonArray.getJSONObject(j).get(fieldList.get(i)));
            }
        }
        return strArray;
    }
}
