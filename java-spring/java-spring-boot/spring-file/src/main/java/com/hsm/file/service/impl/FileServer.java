package com.hsm.file.service.impl;

import com.hsm.file.entity.MyFile;
import com.hsm.file.service.IFileServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    @Override
    public void download(String path, HttpServletResponse response) {
        OutputStream outputStreamDownload  = null;
        InputStream in = null;
        try {
            response.setContentType("application/octet-stream");

            File file = new File(path);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(file.getName().getBytes("utf-8"), "ISO8859-1" )+ "\"");
            outputStreamDownload = response.getOutputStream();
            in = new FileInputStream(file);
            //写文件
            int b;
            while ((b = in.read()) != -1) {
                outputStreamDownload.write(b);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("下载文件错误:{}", e);
        } catch (IOException e) {
            log.error("下载文件错误:{}", e);
        } finally {
            try {
                if (outputStreamDownload != null) {
                    outputStreamDownload.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("下载文件错误:{}", e);
            }
        }
    }

}
