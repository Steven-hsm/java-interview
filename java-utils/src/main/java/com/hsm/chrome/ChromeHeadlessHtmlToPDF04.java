package com.hsm.chrome;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import com.ruiyun.jvppeteer.options.PDFOptions;

import java.util.ArrayList;

public class ChromeHeadlessHtmlToPDF04 {
    public static void main(String[] args) throws Exception {
        //自动下载，第一次下载后不会再下载
        BrowserFetcher.downloadIfNotExist(null);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("--no-sandbox");
        arrayList.add("--headless");
        arrayList.add("--print-to-pdf-no-header");
        arrayList.add("--disable-setuid-sandbox");
        //生成pdf必须在无厘头模式下才能生效
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(arrayList).withHeadless(true).build();

        Browser browser = Puppeteer.launch(options);
        Page page = browser.newPage();
        page.goTo("https://www.baidu.com/?tn=98012088_10_dg&ch=3");

        PDFOptions pdfOptions = new PDFOptions();
        pdfOptions.setPath("test.pdf");
        page.pdf(pdfOptions);
        page.close();
        browser.close();
    }
}
