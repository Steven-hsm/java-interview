package com.hsm.word;


import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.hsm.word.util.Utils;

/**
 * @Classname DocumentBuilderInsertHtml
 * @Description TODO
 * @Date 2021/6/18 10:15
 * @Created by huangsm
 */
public class DocumentBuilderInsertHtml {

    public static void main(String[] args) throws Exception {

        //ExStart:DocumentBuilderInsertHtml
        // The path to the documents directory.
        String dataDir = Utils.getDataDir(DocumentBuilderInsertHtml.class);

        // Open the document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertHtml(
                "<P align='right'>Paragraph right</P>" +
                        "<b>Implicit paragraph left</b>" +
                        "<div align='center'>Div center</div>" +
                        "<h1 align='left'>Heading 1 left.</h1>");

        doc.save(dataDir + "output.doc");
        //ExEnd:DocumentBuilderInsertHtml
    }
}
