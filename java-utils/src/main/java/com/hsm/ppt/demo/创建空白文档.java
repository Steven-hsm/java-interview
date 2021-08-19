package com.hsm.ppt.demo;

import org.apache.poi.xslf.usermodel.XMLSlideShow;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @Classname Construction
 * @Description TODO
 * @Date 2021/8/19 9:32
 * @Created by huangsm
 */
public class 创建空白文档 {
    public static void main(String[] args) throws Exception {
        XMLSlideShow ppt = new XMLSlideShow();

        ppt.write(new FileOutputStream("空白PPt.pptx"));

    }
}
