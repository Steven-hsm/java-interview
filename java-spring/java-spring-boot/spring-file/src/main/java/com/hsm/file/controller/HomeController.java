package com.hsm.file.controller;

import com.hsm.file.service.IFileServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView home(@RequestParam("path") String path) {
        return fileServer.getFileDir(path);
    }
}
