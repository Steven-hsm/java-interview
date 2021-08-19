package com.hsm.ppt.demo;

import org.apache.poi.xslf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Classname 创建超链接
 * @Description TODO
 * @Date 2021/8/19 10:26
 * @Created by huangsm
 */
public class 创建超链接 {
    public static void main(String args[]) throws Exception{

        //create an empty presentation
        XMLSlideShow ppt = new XMLSlideShow();

        //getting the slide master object
        XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);

        //select a layout from specified list
        XSLFSlideLayout slidelayout = slideMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);

        //creating a slide with title and content layout
        XSLFSlide slide = ppt.createSlide(slidelayout);

        //selection of title place holder
        XSLFTextShape body = slide.getPlaceholder(1);

        //clear the existing text in the slid
        body.clearText();

        //adding new paragraph
        XSLFTextRun textRun = body.addNewTextParagraph().addNewTextRun();

        //setting the text
        textRun.setText("Tutorials point");

        //creating the hyperlink
        XSLFHyperlink link = textRun.createHyperlink();

        //setting the link address
        link.setAddress("http://www.w3cschool.cn/");

        //create the file object
        File file=new File("hyperlink.pptx");
        FileOutputStream out = new FileOutputStream(file);

        //save the changes in a file
        ppt.write(out);
        System.out.println("slide cretated successfully");
        out.close();
    }
}
