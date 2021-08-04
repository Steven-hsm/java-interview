package com.hsm.brain.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @Classname DateUtils
 * @Description 日期工具类
 * @Date 2021/8/4 17:38
 * @Created by huangsm
 */
public class DateUtils {

    public static Date getIntervalDays(int days){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,days);
        return calendar.getTime();
    }
}
