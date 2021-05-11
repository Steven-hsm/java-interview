package com.hsm.file.service;

import org.springframework.web.servlet.ModelAndView;

/**
 * @Classname IFileServer
 * @Description 文件服务接口
 * @Date 2021/5/11 16:28
 * @Created by huangsm
 */
public interface IFileServer {
    /**
     * 返回文件目录及文件名
     * @param path
     * @return
     */
    ModelAndView getFileDir(String path);
}
