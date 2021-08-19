package com.hsm.ppt.demo;

import org.apache.poi.xslf.usermodel.*;

import java.io.FileOutputStream;

/**
 * @Classname 幻灯片布局
 * @Description TODO
 * @Date 2021/8/19 9:41
 * @Created by huangsm
 */
public class 幻灯片布局 {
    public static void main(String[] args) throws Exception {
       /* XMLSlideShow ppt = new XMLSlideShow();
        List<XSLFSlideMaster> slideMasters = ppt.getSlideMasters();//获取幻灯片主题列表
        for (XSLFSlideMaster slideMaster : slideMasters) {
            XSLFSlideLayout[] slideLayouts = slideMaster.getSlideLayouts();//从幻灯片母带获取幻灯片布局的列表
            for (XSLFSlideLayout slideLayout : slideLayouts) {
                SlideLayout type = slideLayout.getType();//获取幻灯片布局的名称
                System.out.println(type);
            }
        }*/

        XMLSlideShow ppt = new XMLSlideShow();

        XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);
        XSLFSlideLayout layout = slideMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);

        //创建一页幻灯片布局
        XSLFSlide slide = ppt.createSlide(layout);
        //title标题内容限制
        XSLFTextShape title = slide.getPlaceholder(0);
        title.setText("Introduction");
        //文本题设置
        XSLFTextShape body = slide.getPlaceholder(1);
        body.clearText();
        body.addNewTextParagraph().addNewTextRun().setText("this is  my first slide body");

        ppt.write(new FileOutputStream("幻灯片布局.pptx"));
    }
}
