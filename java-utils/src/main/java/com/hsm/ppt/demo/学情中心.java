package com.hsm.ppt.demo;

import org.apache.commons.io.FileUtils;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.sl.usermodel.TextShape;
import org.apache.poi.xslf.usermodel.*;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @Classname 学情中心
 * @Description TODO
 * @Date 2021/8/19 11:37
 * @Created by huangsm
 */
public class 学情中心 {
    public static void main(String[] args) throws Exception {
        XMLSlideShow ppt = new XMLSlideShow();

        //设置背景
        byte[] bt2 = FileUtils.readFileToByteArray(new File("E:\\img\\背景.png"));
        XSLFPictureData idx2 = ppt.addPicture(bt2, XSLFPictureData.PictureType.PNG);
        XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);//可设置母版的
        XSLFPictureShape picture = slideMaster.createPicture(idx2);//设置母版
        picture.setAnchor(new Rectangle(0, 0, ppt.getPageSize().width
                , ppt.getPageSize().height)); //设置母版大小

        XSLFSlide page1 = ppt.createSlide();

        XSLFTextBox textBox = page1.createTextBox();
        textBox.setAnchor(new Rectangle(100, 200,500, 200));
        XSLFTextParagraph paragraph = textBox.addNewTextParagraph();
        paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
        //标题
        XSLFTextRun title = paragraph.addNewTextRun();
        title.setText("教师讲义");
        title.setFontColor(Color.CYAN);
        title.setFontSize(36D);
        //考试名称
        paragraph.addLineBreak();
        XSLFTextRun examName = paragraph.addNewTextRun();
        examName.setText("东莞市光明中学2020-2021学年度第二学期七年级第四次周测（DGGM720）");
        examName.setFontColor(Color.white);
        examName.setFontSize(20D);

        //班级名称
        paragraph.addLineBreak();
        paragraph.addLineBreak();
        XSLFTextRun examInfo = paragraph.addNewTextRun();
        examInfo.setText("高二02班数学 2021.07.18");
        examInfo.setFontColor(Color.yellow);
        examInfo.setFontSize(15D);


        ppt.write(new FileOutputStream("教师讲义.pptx"));
    }
}
