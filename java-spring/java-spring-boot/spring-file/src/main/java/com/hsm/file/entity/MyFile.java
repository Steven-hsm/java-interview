package com.hsm.file.entity;

import lombok.Data;

/**
 * @Classname MyFile
 * @Description 文件属性
 * @Date 2021/5/11 16:32
 * @Created by huangsm
 */
@Data
public class MyFile {
    /**
     * 绝对路径
     */
    private String absolutePath;

    private String replacePath;

    public String getReplacePath(){
        return absolutePath
                .replace(" ","%20")
                .replace("\"","%22")
                .replace("#","%23")
                .replace("%","%25")
                .replace("&","%26")
                .replace("(","%28")
                .replace(")","%29")
                .replace("+","%2B")
                .replace(",","%2C")
                .replace("/","%2F")
                .replace(":","%3A")
                .replace(";","%3B")
                .replace("<","%3C")
                .replace("=","%3D")
                .replace(">","%3E")
                .replace("?","%eF")
                .replace("@","%40")
                .replace("\\","%5C")
                .replace("|","%7C")
                ;
    }

    /**
     * 文件明
     */
    private String fileName;

    /**
     * 是否是目录
     */
    private boolean isDir = true;
}
