package com.hsm.es;


import com.hsm.common.CollectionUtils;
import com.hsm.common.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @Classname ClassPromotionQueryVO
 * @Description 班级学情查询参数
 * @Date 2021/5/25 10:05
 * @Created by huangsm
 */
@Data

@NoArgsConstructor
public class ClassPromotionQueryVO {

    private Integer dateCode;

    public String gradeCode;

    public List<String> classIdList;

    public List<String> getClassIdList(){
        if(CollectionUtils.isEmpty(this.classIdList)){
            if(StringUtils.isNotEmpty(this.classIds)){
                return Arrays.asList(this.classIds.split(","));
            }
        }
        return this.classIdList;
    }

    public String classIds;

    public String subjectCode;

    public String orgId;

    public String yearId;

}
