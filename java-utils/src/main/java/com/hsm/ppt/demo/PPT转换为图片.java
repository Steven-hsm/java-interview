package com.hsm.ppt.demo;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Classname PPT转换为图片
 * @Description TODO
 * @Date 2021/8/19 11:14
 * @Created by huangsm
 */
public class PPT转换为图片 {
    public static void main(String args[]) throws IOException {

        //creating an empty presentation
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream("202009271714291429.pptx"));

        //getting the dimensions and size of the slide
        Dimension pgsize = ppt.getPageSize();
        List<XSLFSlide> slides = ppt.getSlides();

        BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = img.createGraphics();
        for (int i = 0; i < slides.size(); i++) {
            //clear the drawing area
            //graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

            //render
            slides.get(i).draw(graphics);
            //creating an image file as output
            FileOutputStream out = new FileOutputStream(String.format("image/ppt_image%s.png", i));
            javax.imageio.ImageIO.write(img, "png", out);
            ppt.write(out);
            out.close();
        }

        System.out.println("Image successfully created");

    }
}
