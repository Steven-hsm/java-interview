package com.hsm.rocketmq.tree;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname Main
 * @Description TODO
 * @Date 2021/6/8 20:22
 * @Created by huangsm
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String jsonData = Main.readJsonFile("E:\\github\\java-interview\\rocketMq\\src\\main\\java\\com\\hsm\\rocketmq\\tree\\data.json");
        List<MyData> myDataList = JSON.parseArray(jsonData, MyData.class);
        //1. 开始准备数据
        Map<String, String> gradeNameMap = new HashMap<>();
        Map<String, String> classNameMap = new HashMap<>();
        Map<String, String> subjectNameMap = new HashMap<>();

        Set<String> gradeCode = myDataList.stream().map(MyData::getGrade_code).collect(Collectors.toSet());

        Map<String, Set<String>> grade_classMap = new HashMap<>();
        Map<String, Set<String>> gradeclass_subjectMap = new HashMap<>();
        for (MyData myData : myDataList) {
            gradeNameMap.put(myData.getGrade_code(),myData.getGrade_name());
            classNameMap.put(myData.getClass_id(),myData.getClass_name());
            subjectNameMap.put(myData.getSubject_id(),myData.getSubject_name());

            Set<String> classIdSet = Optional.ofNullable(grade_classMap.get(myData.getGrade_code())).orElse(new HashSet<>());
            classIdSet.add(myData.getClass_id());
            grade_classMap.put(myData.getGrade_code(), classIdSet);

            String gradeclassKey = myData.getGrade_code() + "#" + myData.getClass_id();
            Set<String> subjectSet = Optional.ofNullable(gradeclass_subjectMap.get(gradeclassKey))
                    .orElse(new HashSet<>());
            subjectSet.add(myData.getSubject_id());
            gradeclass_subjectMap.put(gradeclassKey, subjectSet);
        }
        //2. 开始组装数据
        List<GradeDataBO> dataList = new ArrayList<>();
        for (String grade_code : gradeCode) {
            GradeDataBO gradeDataBO = new GradeDataBO();
            gradeDataBO.setGradeCode(grade_code);
            gradeDataBO.setGradeName(gradeNameMap.get(grade_code));


            Set<String> classIds = grade_classMap.get(grade_code);
            List<ClassDataBO> classList = new ArrayList<>();
            for (String classId : classIds) {
                ClassDataBO classDataBO = new ClassDataBO();
                classDataBO.setClassId(classId);
                classDataBO.setClassName(classNameMap.get(classId));


                String gradeclassKey = grade_code + "#" + classId;
                Set<String> subjectCodes = gradeclass_subjectMap.get(gradeclassKey);
                List<SubjectDataBO> subjectList = new ArrayList<>();
                for (String subjectCode : subjectCodes) {
                    SubjectDataBO subjectDataBO = new SubjectDataBO();
                    subjectDataBO.setSubjectCode(subjectCode);
                    subjectDataBO.setSubjectName(subjectNameMap.get(subjectCode));
                    subjectList.add(subjectDataBO);
                }
                classDataBO.setSubjectList(subjectList);
                classList.add(classDataBO);
            }
            gradeDataBO.setClassList(classList);
            dataList.add(gradeDataBO);
        }
        System.out.println("最终数据：" + JSON.toJSONString(dataList));
    }


    //读取json文件
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
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

