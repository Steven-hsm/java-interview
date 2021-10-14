package com.hsm.word;

import com.aspose.words.*;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;

public class DocAddHeader {

    public static void main(String[] args) throws Exception {
        Document doc = new Document("E:\\page\\Maven私服切换注意事项.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        AddHeaderFooter(builder);

        doc.save("E:\\page\\AddHeaderFooter.docx");
    }

    private static void AddHeaderFooter(DocumentBuilder builder) throws Exception {
        Section currentSection = builder.getCurrentSection();
        PageSetup pageSetup = currentSection.getPageSetup();
        pageSetup.setDifferentFirstPageHeaderFooter(true);

        //pageSetup.setHeaderDistance(20);
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_FIRST);
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        //builder.getParagraphFormat().setLineSpacing(12);// 单倍行距 = 12 ， 1.5 倍 = 18
        builder.getParagraphFormat().setSpaceAfter(0);//段后
        builder.getParagraphFormat().setSpaceBefore(0);//段前
        builder.insertImage(new FileInputStream(new File("E:\\github\\java-interview\\java-utils\\src\\main\\java\\com\\hsm\\word\\header1.png")));

        //pageSetup.setHeaderDistance(20);
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        //builder.getParagraphFormat().setLineSpacing(12);// 单倍行距 = 12 ， 1.5 倍 = 18
        builder.getParagraphFormat().setSpaceAfter(0);//段后
        builder.getParagraphFormat().setSpaceBefore(0);//段前
        builder.insertImage(new FileInputStream(new File("E:\\github\\java-interview\\java-utils\\src\\main\\java\\com\\hsm\\word\\header2.png")));
    }
}
