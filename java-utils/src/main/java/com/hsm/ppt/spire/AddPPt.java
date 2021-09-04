//package com.hsm.ppt.spire;
//
//import com.spire.presentation.FileFormat;
//import com.spire.presentation.Presentation;
//import com.spire.presentation.collections.ShapeList;
//
///**
// * @Classname AddPPt
// * @Description TODO
// * @Date 2021/9/2 17:41
// * @Created by huangsm
// */
//public class AddPPt {
//
//    public static void main(String[] args) throws Exception {
//        //实例化一个PPT对象
//        Presentation ppt = new Presentation();
//        //获取第一张幻灯片中的shapes
//        ShapeList shapes = ppt.getSlides().get(0).getShapes();
//        //插入HTML到shapes
//        String code ="<span style=\\\"vertical-align:middle\\\">图中的侵蚀面是由于地壳整体抬升后受外力侵蚀而形成的，读图可知，图中总共有4个侵蚀面，由此可以推测该地经历了4次地壳整体抬升运动。故选C。</span>";
//        shapes.addFromHtml(code);
//        //保存文档
//        String outputFile = "output/result2.pptx";
//        ppt.saveToFile(outputFile, FileFormat.PPTX_2010);
//    }
//}
