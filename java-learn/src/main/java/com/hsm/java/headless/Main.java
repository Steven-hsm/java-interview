package com.hsm.java.headless;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class Main {

    public static void main(String[] args) throws Exception{
        ArrayList<String> argList = new ArrayList<>();
        BrowserFetcher.downloadIfNotExist(null);
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(true).build();
      /*  argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");*/
        Browser browser = Puppeteer.launch(options);

        List<Page> pages = browser.pages();
        if(pages != null && pages.size() > 0){
            pages.get(0).goTo("https://www.baidu.com");
        }else{
            browser.newPage().goTo("https://www.baidu.com",true);
        }
        /*browser.*/
        browser.close();
        log.info("处理完成");
    }
}
