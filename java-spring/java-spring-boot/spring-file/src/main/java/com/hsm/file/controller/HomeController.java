package com.hsm.file.controller;

import com.hsm.file.service.IFileServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Classname HomeController
 * @Description TODO
 * @Date 2021/5/10 10:05
 * @Created by huangsm
 */
@Controller
@Slf4j
public class HomeController {
    @Autowired
    private IFileServer fileServer;

    @RequestMapping(value = {"/home","/"})
    public ModelAndView home(String path) throws UnsupportedEncodingException {
        if(!StringUtils.isEmpty(path)){
            path = URLDecoder.decode(path,"UTF-8");
        }
        return fileServer.getFileDir(path);
    }

    @RequestMapping(value = {"/download"})
    public void download(@RequestParam("path") String path, HttpServletResponse response) throws UnsupportedEncodingException {
        path = URLDecoder.decode(path,"UTF-8");
        fileServer.download(path,response);
    }
}
