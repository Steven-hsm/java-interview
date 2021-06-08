package com.hsm.rocketmq;

import com.hsm.rocketmq.ClassDataBO;
import lombok.Data;

import java.util.List;

/**
 * @Classname TeacherPowerDataBO
 * @Description 年级数据列表
 * @Date 2021/5/25 9:43
 * @Created by huangsm
 */
@Data
public class GradeDataBO {
    private int gradeCode;

    private String gradeName;

    private List<ClassDataBO> classList;
}
