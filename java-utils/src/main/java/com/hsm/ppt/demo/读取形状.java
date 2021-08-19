package com.hsm.ppt.demo;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @Classname 读取形状
 * @Description TODO
 * @Date 2021/8/19 11:07
 * @Created by huangsm
 */
public class 读取形状 {
    public static void main(String[] args) throws Exception {

        //creating a slideshow
        File file = new File("shapes.pptx");
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(file));

        //get slides
        List<XSLFSlide> slides = ppt.getSlides();

        //getting the shapes in the presentation
        System.out.println("Shapes in the presentation:");
        for (int i = 0; i < slides.size(); i++){

            List<XSLFShape> shapes = slides.get(i).getShapes();
            for (int j = 0; j < shapes.size(); j++){

                //name of the shape
                System.out.println(shapes.get(j).getShapeName());
            }
        }

        FileOutputStream out = new FileOutputStream(file);
        ppt.write(out);
        out.close();
    }
}
