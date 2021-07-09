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
                "<p>在<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mo>&#9651;</mo><mi mathvariant=\"italic\">ABC</mi></math>中，<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mi mathvariant=\"italic\">AD</mi></math>为<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mi mathvariant=\"italic\">BC</mi></math>边上的中线，<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mi>E</mi></math>为<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mi mathvariant=\"italic\">AD</mi></math>的中点，</p>\n" +
                        "\n" +
                        "<p><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mover><mrow><mi>E</mi><mi>B</mi></mrow><mo>&#8594;</mo></mover><mo>=</mo><mover><mrow><mi>A</mi><mi>B</mi></mrow><mo>&#8594;</mo></mover><mo>&#8722;</mo><mover><mrow><mi>A</mi><mi>E</mi></mrow><mo>&#8594;</mo></mover><mo>=</mo><mover><mrow><mi>A</mi><mi>B</mi></mrow><mo>&#8594;</mo></mover><mo>&#8722;</mo><mfrac><mn>1</mn><mn>2</mn></mfrac><mover><mrow><mi>A</mi><mi>D</mi></mrow><mo>&#8594;</mo></mover></math></p>\n" +
                        "\n" +
                        "<p><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mo>=</mo><mover><mrow><mi>A</mi><mi>B</mi></mrow><mo>&#8594;</mo></mover><mo>&#8722;</mo><mfrac><mn>1</mn><mn>2</mn></mfrac><mo>&#215;</mo><mfrac><mn>1</mn><mn>2</mn></mfrac><mi mathvariant=\"normal\">(</mi><mover><mrow><mi>A</mi><mi>B</mi></mrow><mo>&#8594;</mo></mover><mo>+</mo><mover><mrow><mi>A</mi><mi>C</mi></mrow><mo>&#8594;</mo></mover><mi mathvariant=\"normal\">)</mi></math></p>\n" +
                        "\n" +
                        "<p><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mo>=</mo><mfrac><mn>3</mn><mn>4</mn></mfrac><mover><mrow><mi>A</mi><mi>B</mi></mrow><mo>&#8594;</mo></mover><mo>&#8722;</mo><mfrac><mn>1</mn><mn>4</mn></mfrac><mover><mrow><mi>A</mi><mi>C</mi></mrow><mo>&#8594;</mo></mover></math>，</p>\n" +
                        "\n" +
                        "<p>故选：A.</p>\n");

        doc.save(dataDir + "output.doc");
        //ExEnd:DocumentBuilderInsertHtml
    }
}
