package com.hsm.rocketmq.tree;

import lombok.Data;

import java.util.List;

/**
 * @Classname ClassDataBO
 * @Description 班级信息数据
 * @Date 2021/5/25 9:47
 * @Created by huangsm
 */
@Data
public class ClassDataBO {
    private String classId;

    private String className;

    private List<SubjectDataBO> subjectList;
}
