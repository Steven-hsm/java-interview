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

    /**
     * 文件明
     */
    private String fileName;

    /**
     * 是否是目录
     */
    private boolean isDir = true;
}
