package com.hsm.ppt.demo;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Classname 合并
 * @Description TODO
 * @Date 2021/8/19 11:12
 * @Created by huangsm
 */
public class 合并 {
    public static void main(String args[]) throws IOException {

        //creating empty presentation
        XMLSlideShow ppt = new XMLSlideShow();

        //taking the two presentations that are to be merged
        String[] inputs = {"T格式化文本.pptx","两页空白.pptx","幻灯片布局.pptx","hyperlink.pptx"};

        for(String arg : inputs){

            FileInputStream inputstream = new FileInputStream(arg);
            XMLSlideShow src = new XMLSlideShow(inputstream);

            for(XSLFSlide srcSlide : src.getSlides()){

                //merging the contents
                ppt.createSlide().importContent(srcSlide);
            }
        }

        String file3 = "combinedpresentation.pptx";

        //creating the file object
        FileOutputStream out = new FileOutputStream(file3);

        // saving the changes to a file
        ppt.write(out);
        System.out.println("Merging done successfully");
        out.close();
    }
}
