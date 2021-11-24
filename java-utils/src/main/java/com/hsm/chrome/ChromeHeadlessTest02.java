package com.hsm.chrome;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;

import java.util.ArrayList;
import java.util.List;

public class ChromeHeadlessTest02 {
    public static void main(String[] args) throws Exception {
        //自动下载，第一次下载后不会再下载
        BrowserFetcher.downloadIfNotExist(null);
        ArrayList<String> argList = new ArrayList<>();
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false).build();
        argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");
        Browser browser = Puppeteer.launch(options);
        //Browser browser2 = Puppeteer.launch(options);
        List<Page> pages = browser.pages();
        for (Page page : pages) {
            page.goTo("https://www.baidu.com");
        }

       /* Page page = browser.newPage();
        page.goTo("https://www.baidu.com");
        browser.close();

        Page page1 = browser2.newPage();
        page1.goTo("https://www.baidu.com");*/
    }
}
