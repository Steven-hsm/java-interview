package com.hsm.ppt.demo;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @Classname KnpRate
 * @Description TODO
 * @Date 2021/8/23 10:42
 * @Created by huangsm
 */
@Data
public class KnpRate {
    private String knpName;
    private String stars;
    private String classRate;
    private Integer errorPeople;
    private String questionNos;

    public KnpRate(String knpName, String stars, String classRate, Integer errorPeople, String questionNos) {
        this.knpName = knpName;
        this.stars = stars;
        this.classRate = classRate;
        this.errorPeople = errorPeople;
        this.questionNos = questionNos;
    }

    public static List<KnpRate> getList(){
        return Arrays.asList(
                new KnpRate("什么鬼","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("平行四边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("正三角形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("五边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("六边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("什么鬼","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("平行四边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("正三角形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("五边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("六边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("什么鬼","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("平行四边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("正三角形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("五边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("六边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("什么鬼","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("平行四边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("正三角形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("五边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("六边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("什么鬼","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("平行四边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("正三角形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("五边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("六边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("什么鬼","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("平行四边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("正三角形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("五边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("六边形","*****","30%",5,"1、3、5、6、8"),
                new KnpRate("算术平方根","*****","30%",5,"1、3、5、6、8")
        );
    }
}
