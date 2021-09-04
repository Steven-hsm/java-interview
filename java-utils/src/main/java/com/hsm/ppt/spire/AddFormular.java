/*
package com.hsm.ppt.spire;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.omath.OfficeMath;

*/
/**
 * @Classname AddFormular
 * @Description TODO
 * @Date 2021/9/2 17:36
 * @Created by huangsm
 *//*

public class AddFormular {
    public static void main(String[] args) {
        //新建Word示例，添加一个section
        Document doc = new Document();
        Section section = doc.addSection();

        //添加段落1和段落2,添加Latex数学公式
        Paragraph paragraph1 = section.addParagraph();
        OfficeMath officeMath1 = new OfficeMath(doc);
        paragraph1.getItems().add(officeMath1);
        officeMath1.fromLatexMathCode("$f(x, y) = 100 * \\lbrace[(x + y) * 3] - 5\\rbrace$");

        Paragraph paragraph2 = section.addParagraph();
        OfficeMath officeMath2 = new OfficeMath(doc);
        paragraph2.getItems().add(officeMath2);
        officeMath2.fromLatexMathCode("$S=a_{1}^2+a_{2}^2+a_{3}^2$");

        //添加段落3，插入MathML数学公式
        Paragraph paragraph3 = section.addParagraph();
        OfficeMath officeMath3 = new OfficeMath(doc);
        paragraph3.getItems().add(officeMath3);
        officeMath3.fromMathMLCode("<mml:math xmlns:mml=\"http://www.w3.org/1998/Math/MathML\" xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\"><mml:msup><mml:mrow><mml:mi>x</mml:mi></mml:mrow><mml:mrow><mml:mn>2</mml:mn></mml:mrow></mml:msup><mml:mo>+</mml:mo><mml:msqrt><mml:msup><mml:mrow><mml:mi>x</mml:mi></mml:mrow><mml:mrow><mml:mn>2</mml:mn></mml:mrow></mml:msup><mml:mo>+</mml:mo><mml:mn>1</mml:mn></mml:msqrt><mml:mo>+</mml:mo><mml:mn>1</mml:mn></mml:math>");

        //保存文档
        doc.saveToFile("addMathEquation.docx", FileFormat.Docx_2013);
        doc.dispose();
    }
}
*/
