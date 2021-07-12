package com.hsm.brain.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Classname SqlUtilsTest
 * @Description sql工具类测试
 * @Date 2021/7/12 15:21
 * @Created by huangsm
 */
public class SqlUtilsTest {

    @Test
    public void contactLike() {
        Assert.assertEquals(SqlUtils.contactLike("测试"),"%测试%");
    }
}