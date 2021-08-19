package com.hsm.ppt.demo;

import org.apache.commons.io.IOUtils;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @Classname 图片
 * @Description TODO
 * @Date 2021/8/19 10:11
 * @Created by huangsm
 */
public class 图片 {
    public static void main(String[] args) throws Exception {
        XMLSlideShow ppt = new XMLSlideShow();
        XSLFSlide slide = ppt.createSlide();

        File image=new File("E:\\img\\减肥.png");
        byte[] picture = IOUtils.toByteArray(new FileInputStream(image));

        XSLFPictureData pictureData = ppt.addPicture(picture, PictureData.PictureType.PNG);

        slide.createPicture(pictureData);

        ppt.write(new FileOutputStream("图片.pptx"));
    }
}
