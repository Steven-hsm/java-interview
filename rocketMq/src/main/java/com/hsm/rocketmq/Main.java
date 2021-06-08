package com.hsm.rocketmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Classname Main
 * @Description TODO
 * @Date 2021/6/8 20:22
 * @Created by huangsm
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String jsonData = Main.readJsonFile("C:\\Users\\Admin\\Desktop\\data.json");
        List<MyData> myData = JSON.parseArray(jsonData, MyData.class);

        List<GradeDataBO> dataList = new ArrayList<>();






    }



    //读取json文件
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
