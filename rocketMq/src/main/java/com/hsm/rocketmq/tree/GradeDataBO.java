package com.hsm.rocketmq.tree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname TeacherPowerDataBO
 * @Description 年级数据列表
 * @Date 2021/5/25 9:43
 * @Created by huangsm
 */
@Data
public class GradeDataBO {
    private String gradeCode;

    private String gradeName;

    private List<ClassDataBO> classList = new ArrayList<>();
}
