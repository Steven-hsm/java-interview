package com.hsm.es;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname StudentScoreSingleDTO
 * @Description TODO
 * @Date 2021/7/8 11:14
 * @Created by huangsm
 */
@Data
public class StudentScoreSingleDTO implements Serializable {

    private static final Long serialVersionUID = 1357856839949058288L;

    private String id;
    /**
     * 考试id
     */


    private String examId;
    /**
     * 考试类型
     */


    private String examType;
    /**
     * 考试级别
     */


    private Integer examLevel;



    private Date examDate;
    /**
     * 试卷id
     */


    private String paperId;
    /**
     * 学生id
     */


    private String studentId;
    /**
     * 学号
     */


    private String stuNo;
    /**
     * 考号
     */


    private String zkzh;
    /**
     * 学生姓名
     */


    private String stuName;
    /**
     * 座位号
     */


    private String seatNo;
    /**
     * 组织id
     */


    private String orgId;
    /**
     * 组织名称
     */


    private String orgName;
    /**
     * 年级
     */


    private Integer gradeCode;
    /**
     * 行政班id
     */


    private String classId;
    /**
     * 行政班名称
     */


    private String className;
    /**
     * 教学班id
     */


    private String classGroupId;
    /**
     * 教学班名称
     */


    private String classGroupName;
    /**
     * 科目code
     */


    private String subjectCode;
    /**
     * 科目名称
     */


    private String subjectName;
    /**
     * 选科code
     */


    private String optionSubjectCode;
    /**
     * 选科名称
     */


    private String optionSubjectName;
    /**
     * 科目满分
     */


    private Float fullScore;
    /**
     * 客观题满分
     */


    private Float fullKScore;
    /**
     * 主观题满分
     */


    private Float fullZScore;
    /**
     * 最终分数
     */


    private Float score;
    /**
     * 计算分数
     */


    private Float conversionScore;
    /**
     * 原始分数
     */


    private Float originalScore;
    /**
     * 客观题分数
     */


    private Float kScore;
    /**
     * 客观题分数
     */


    private Float fjScore;
    /**
     * 主观题分数
     */


    private Float zScore;
    /**
     * 总排名
     */


    private Integer totalRanking;
    /**
     * 学校排名
     */


    private Integer schoolRanking;

    /**
     * 班级排名
     */


    private Integer classRanking;

    /**
     * 文 理 或 无
     */


    private Integer type;
    /**
     * 考试档次档次
     */


    private Integer level;

    /**
     * 考试档次档次
     */


    private String levelName;
    /**
     * 学校排名档次
     */


    private Integer schoolLevel;
    /**
     * 是否违纪 1为违纪，0为不违纪（默认）
     */


    private Integer isBreach;
    /**
     * 是否缺考 1为缺考，0为不缺考(默认)
     */


    private Integer isMissingExam;
    /**
     * 是否扫描 1未扫描，0为扫描(默认)
     */


    private Integer isScanning;
    /**
     * 是否学考 0：否 1：是
     */


    private Integer isAcademicExam;
    /**
     * 是否有效数据，基于参考规则，计算值
     * 0：无效，1：有效
     */


    private Integer isEffective;


    public StudentScoreSingleDTO() {
        this.totalRanking = -1;
        this.schoolRanking = -1;
        this.classRanking = -1;
        this.isEffective = 0;
    }
}
