package com.hsm.file.service.impl;

import com.hsm.file.entity.MyFile;
import com.hsm.file.service.IFileServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname FileServer
 * @Description 文件服务器
 * @Date 2021/5/11 16:28
 * @Created by huangsm
 */
@Service
@Slf4j
public class FileServer implements IFileServer {

    @Override
    public ModelAndView getFileDir(String path) {
        ModelAndView modelAndView = new ModelAndView();
        List<MyFile> dirList = new ArrayList<>();
        List<MyFile> fileList = new ArrayList<>();
        File[] files = null;
        if(StringUtils.isEmpty(path)){
            files = File.listRoots();
        }else{
            File file = new File(path);
            files = file.listFiles();
        }
        for (File file : files) {
            MyFile myFile = new MyFile();
            if(file.isDirectory()){
                myFile.setFileName(file.getName());
                myFile.setAbsolutePath(file.getAbsolutePath());
                myFile.setDir(true);
                dirList.add(myFile);
            }else{
                myFile.setFileName(file.getName());
                myFile.setAbsolutePath(file.getAbsolutePath());
                myFile.setDir(false);
                fileList.add(myFile);
            }
        }
        modelAndView.addObject("dirList",dirList);
        modelAndView.addObject("fileList",fileList);
        modelAndView.setViewName("freemarker");

        return modelAndView;
    }
}
