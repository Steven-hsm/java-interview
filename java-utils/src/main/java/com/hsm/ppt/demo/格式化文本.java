package com.hsm.ppt.demo;

import org.apache.poi.xslf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Classname 格式化文本
 * @Description TODO
 * @Date 2021/8/19 11:09
 * @Created by huangsm
 */
public class 格式化文本 {
    public static void main(String args[]) throws IOException {

        //creating an empty presentation
        XMLSlideShow ppt = new XMLSlideShow();

        //getting the slide master object
        XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);

        //select a layout from specified list
        XSLFSlideLayout slidelayout = slideMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);

        //creating a slide with title and content layout
        XSLFSlide slide = ppt.createSlide(slidelayout);

        //selection of title place holder
        XSLFTextShape body = slide.getPlaceholder(1);

        //clear the existing text in the slide
        body.clearText();

        //adding new paragraph
        XSLFTextParagraph paragraph=body.addNewTextParagraph();

        //formatting line 1

        XSLFTextRun run1 = paragraph.addNewTextRun();
        run1.setText("This is a colored line");

        //setting color to the text
        run1.setFontColor(java.awt.Color.red);

        //setting font size to the text
        run1.setFontSize(24D);

        //moving to the next line
        paragraph.addLineBreak();

        //formatting line 2

        XSLFTextRun run2 = paragraph.addNewTextRun();
        run2.setText("This is a bold line");
        run2.setFontColor(java.awt.Color.CYAN);

        //making the text bold
        run2.setBold(true);
        paragraph.addLineBreak();

        //formatting line 3

        XSLFTextRun run3 = paragraph.addNewTextRun();
        run3.setText(" This is a striked line");
        run3.setFontSize(12D);

        //making the text italic
        run3.setItalic(true);

        //strike through the text
        run3.setStrikethrough(true);
        paragraph.addLineBreak();

        //formatting line 4

        XSLFTextRun run4 = paragraph.addNewTextRun();
        run4.setText(" This an underlined line");
        run4.setUnderlined(true);

        //underlining the text
        paragraph.addLineBreak();

        //creating a file object
        File file=new File("T格式化文本.pptx");
                FileOutputStream out = new FileOutputStream(file);

        //saving the changes to a file
        ppt.write(out);
        out.close();
    }
}
