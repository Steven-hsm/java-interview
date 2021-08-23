package com.hsm.ppt.demo;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @Classname ErrorRate
 * @Description 试题错误率分析
 * @Date 2021/8/23 9:08
 * @Created by huangsm
 */
@Data
public class ErrorRate {
    /**
     * 题号
     */
    private String qustionNo;
    /**
     * 题号
     */
    private Integer errorPeople;

    /**
     * 题号
     */
    private Float errorRate;

    public ErrorRate(String qustionNo, Integer errorPeople, Float errorRate) {
        this.qustionNo = qustionNo;
        this.errorPeople = errorPeople;
        this.errorRate = errorRate;
    }

    public static List<ErrorRate> getList(){
        return Arrays.asList(new ErrorRate(""+1,10,35F),
                new ErrorRate("" +2,10,35F),
                new ErrorRate("" +3,10,35F),
                new ErrorRate("" +4,10,35F),
                new ErrorRate("" +5,10,35F),
                new ErrorRate("" +6,10,35F),
                new ErrorRate("" +7,10,35F),
                new ErrorRate("" +8,10,35F),
                new ErrorRate("" +9,10,35F),
                new ErrorRate("" +10,10,35F),
                new ErrorRate("" +11,10,35F),
                new ErrorRate("" +12,10,35F),
                new ErrorRate("" +13,10,35F),
                new ErrorRate("" +14,10,35F),
                new ErrorRate("" +15,10,35F),
                new ErrorRate("" +16,10,35F),
                new ErrorRate("" +17,10,35F),
                new ErrorRate("" +18,10,35F),
                new ErrorRate("" +19,10,35F),
                new ErrorRate("" +20,10,35F),
                new ErrorRate("" +21,10,35F),
                new ErrorRate("" +22,10,35F),
                new ErrorRate("" +23,10,35F),
                new ErrorRate("" +24,10,35F),
                new ErrorRate("" +25,10,35F),
                new ErrorRate("" +26,10,35F),
                new ErrorRate("" +27,10,35F),
                new ErrorRate("" +28,10,35F),
                new ErrorRate("" +29,10,35F),
                new ErrorRate("" +30,10,35F),
                new ErrorRate("" +31,10,35F));
    }
}


