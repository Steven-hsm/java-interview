package com.hsm.ppt.demo;

import org.apache.poi.xslf.usermodel.XMLSlideShow;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @Classname 幻灯片管理
 * @Description TODO
 * @Date 2021/8/19 9:57
 * @Created by huangsm
 */
public class 幻灯片管理 {
    public static void main(String[] args) throws Exception {
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream("幻灯片布局.pptx"));
        Dimension pageSize = ppt.getPageSize();
        ppt.setPageSize(new Dimension(1024,768));

        ppt.write(new FileOutputStream("幻灯片管理.pptx"));
    }
}
