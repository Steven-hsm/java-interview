package com.hsm.base64;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * @Classname ImageUtil
 * @Description TODO
 * @Date 2021/6/29 17:53
 * @Created by huangsm
 */
@Slf4j
public class ImageUtil {
    public static void base64StringToImage(String base64String,String filePath) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            File f1 = new File(filePath);
            ImageIO.write(bi1, "png", f1);
        } catch (IOException e) {
           log.error("文件生成异常",e);
        }
    }
}
