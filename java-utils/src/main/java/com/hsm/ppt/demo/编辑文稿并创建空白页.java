package com.hsm.ppt.demo;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @Classname 编辑文稿
 * @Description TODO
 * @Date 2021/8/19 9:37
 * @Created by huangsm
 */
public class 编辑文稿并创建空白页 {
    public static void main(String[] args) throws Exception {
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream("空白PPt.pptx"));
        XSLFSlide page1 = ppt.createSlide();
        XSLFSlide page2 = ppt.createSlide();
        ppt.write(new FileOutputStream("两页空白.pptx"));
    }
}
