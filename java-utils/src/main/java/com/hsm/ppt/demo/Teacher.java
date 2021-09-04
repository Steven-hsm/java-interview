package com.hsm.ppt.demo;

import org.apache.poi.sl.usermodel.TableCell;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @Classname Teacher
 * @Description TODO
 * @Date 2021/8/20 11:22
 * @Created by huangsm
 */
public class Teacher {
    public static void main(String[] args) throws Exception {
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream("teacher.pptx"));
        //获取ppt数据
        List<XSLFSlide> slides = ppt.getSlides();
        //处理一页输数据，试卷名称
        dealFirst(slides.get(0), "东莞市光明中学2020—2021学年度第二学期七年级第十次周测（DGGM720)", " 高二02班数学 2021.07.18");
        //处理第三页班级情况分析
        dealThree(slides.get(2));
        //处理第四页试题错误率分析
        dealFour(slides.get(3));
        //处理第五页知识点掌握情况
        dealFive(ppt, slides.get(4));
        //班级共性错题
        dealCommonError(ppt);
        //班级其他错题
        dealOtherError(ppt);

        ppt.write(new FileOutputStream(String.format("教师讲义%s.pptx",new Random().nextInt(11000))));
    }

    /**
     * 处理班级其他错题
     * @param ppt
     */
    private static void dealOtherError(XMLSlideShow ppt) {
        XSLFSlide slide = ppt.createSlide();

        //第一页需要加上04 班级共性错题
        XSLFTextBox textBox = slide.createTextBox();
        textBox.setAnchor(new Rectangle(30,20,200,30));
        XSLFTextParagraph paragraph = getTextParagraph(textBox);
        paragraph.setTextAlign(TextParagraph.TextAlign.LEFT);
        XSLFTextRun title = getTextRun(paragraph);
        title.setText("05  班级其他错题");
        title.setFontColor(new Color(138, 204, 80));
        title.setFontSize(18D);

        //第一页需要加上04 班级共性错题
        XSLFTextBox textBox_title = slide.createTextBox();
        textBox_title.setAnchor(new Rectangle(30,70,65,25));
        textBox_title.setFillColor(new Color(103, 178, 170));
        XSLFTextParagraph paragraph_title = getTextParagraph(textBox_title);
        paragraph_title.setTextAlign(TextParagraph.TextAlign.LEFT);

        XSLFTextRun title_question = getTextRun(paragraph_title);
        title_question.setText(String.format("第%s题",5));
        title_question.setFontColor(Color.white);
        title_question.setFontSize(16D);

        //班级得分率相关的
        XSLFTextBox textBox_scoreRate = slide.createTextBox();
        textBox_scoreRate.setAnchor(new Rectangle(100,73,550,15));
        XSLFTextParagraph paragraph_scoreRate = getTextParagraph(textBox_scoreRate);
        paragraph_scoreRate.setTextAlign(TextParagraph.TextAlign.LEFT);

        XSLFTextRun title_scoreRate = getTextRun(paragraph_scoreRate);
        title_scoreRate.setText(String.format("班级得分率:%s，年级得分率：%s，答对：%s人，答错：%s人","0.21%","45.99%",4,53));
        title_scoreRate.setFontColor(Color.white);
        title_scoreRate.setFontSize(12D);

        //答错学生
        XSLFTextBox textBox_errorPeople = slide.createTextBox();
        textBox_errorPeople.setAnchor(new Rectangle(33,100,500,50));
        XSLFTextParagraph paragraph_errorPeople = getTextParagraph(textBox_errorPeople);
        paragraph_errorPeople.setTextAlign(TextParagraph.TextAlign.LEFT);

        XSLFTextRun title_errorDesc= getTextRun(paragraph_errorPeople);
        title_errorDesc.setText("答错学生:   ");
        title_errorDesc.setFontColor(Color.RED);
        title_errorDesc.setFontSize(8D);

        XSLFTextRun title_errorPeople= getTextRun(paragraph_errorPeople);
        title_errorPeople.setText("邓舒月、刁卉、李美仪、蔡浩霖、陈嘉利、陈铭诗、陈俊澄、陈文俊、李乐诗、耿言、李琦、孔炫斌、杜君豪、崔珏峥、任娟、" +
                "潘紫恩、梁欣然15、陆悦、庞珊、罗兆俊、林梦茨、李扬波、刘丰铭、盘欣峒、欧洵傲、邱煜博、邵恩、徐义沣、吴雪静、先玺" +
                "蓉、徐卓君、谢洋、许安琦、谢雅琪、谭晰元、向攀嵩、谢思进、徐雨菲、姚钧杰、许培昇、徐子琪、詹永濠、张炜锋、林敬" +
                "圣、钟卓霖、邹乐欣、范轩毓、游智翔、郑依琪、张明哲、陈梓豪、张政、郑子莹");
        title_errorPeople.setFontColor(Color.white);
        title_errorPeople.setFontSize(8D);

        //原题信息
        XSLFTextBox textBox_question = slide.createTextBox();
        textBox_question.setAnchor(new Rectangle(30,160,500,200));
        textBox_question.setFillColor(Color.white);
        XSLFTextParagraph paragraph_question = getTextParagraph(textBox_question);
        paragraph_question.setTextAlign(TextParagraph.TextAlign.LEFT);

        XSLFTextRun text_question = getTextRun(paragraph_question);
        text_question.setText(String.format("在平面直角坐标系中，点 一定在第（ ）象限\n" +
                "  A. 第一象限     B. 第二象限\n" +
                "  C. 第三象限     D. 第四象限\n" +
                "\n" +
                "【答案】B\n" +
                "【解析】\n" +
                "∵点 它的横坐标 ，纵坐标 ， ∴符合点在第二象限的条件，故点 一定在第二象限．\n" +
                "故选：B．\n",2));
        text_question.setFontColor(Color.black);
        text_question.setFontSize(9D);

        dealErrorCase(ppt);
    }


    /**
     * 处理班级共性错题
     * @param ppt
     */
    private static void dealCommonError(XMLSlideShow ppt) {
        XSLFSlide slide = ppt.createSlide();

        //第一页需要加上04 班级共性错题
        XSLFTextBox textBox = slide.createTextBox();
        textBox.setAnchor(new Rectangle(30,20,200,30));
        XSLFTextParagraph paragraph = getTextParagraph(textBox);
        paragraph.setTextAlign(TextParagraph.TextAlign.LEFT);
        XSLFTextRun title = getTextRun(paragraph);
        title.setText("04  班级共性错题");
        title.setFontColor(new Color(103, 178, 170));
        title.setFontSize(18D);

        XSLFTextBox textBox_title = slide.createTextBox();
        textBox_title.setAnchor(new Rectangle(30,70,65,25));
        textBox_title.setFillColor(new Color(103, 178, 170));
        XSLFTextParagraph paragraph_title = getTextParagraph(textBox_title);
        paragraph_title.setTextAlign(TextParagraph.TextAlign.LEFT);

        XSLFTextRun title_question = getTextRun(paragraph_title);
        title_question.setText(String.format("第%s题",2));
        title_question.setFontColor(Color.white);
        title_question.setFontSize(16D);

        //班级得分率相关的
        XSLFTextBox textBox_scoreRate = slide.createTextBox();
        textBox_scoreRate.setAnchor(new Rectangle(100,73,550,15));
        XSLFTextParagraph paragraph_scoreRate = getTextParagraph(textBox_scoreRate);
        paragraph_scoreRate.setTextAlign(TextParagraph.TextAlign.LEFT);

        XSLFTextRun title_scoreRate = getTextRun(paragraph_scoreRate);
        title_scoreRate.setText(String.format("班级得分率:%s，年级得分率：%s，答对：%s人，答错：%s人","0.21%","45.99%",4,53));
        title_scoreRate.setFontColor(Color.white);
        title_scoreRate.setFontSize(12D);

        //答错学生
        XSLFTextBox textBox_errorPeople = slide.createTextBox();
        textBox_errorPeople.setAnchor(new Rectangle(33,100,500,50));
        XSLFTextParagraph paragraph_errorPeople = getTextParagraph(textBox_errorPeople);
        paragraph_errorPeople.setTextAlign(TextParagraph.TextAlign.LEFT);

        XSLFTextRun title_errorDesc= getTextRun(paragraph_errorPeople);
        title_errorDesc.setText("答错学生:   ");
        title_errorDesc.setFontColor(Color.RED);
        title_errorDesc.setFontSize(8D);

        XSLFTextRun title_errorPeople= getTextRun(paragraph_errorPeople);
        title_errorPeople.setText("邓舒月、刁卉、李美仪、蔡浩霖、陈嘉利、陈铭诗、陈俊澄、陈文俊、李乐诗、耿言、李琦、孔炫斌、杜君豪、崔珏峥、任娟、" +
                "潘紫恩、梁欣然15、陆悦、庞珊、罗兆俊、林梦茨、李扬波、刘丰铭、盘欣峒、欧洵傲、邱煜博、邵恩、徐义沣、吴雪静、先玺" +
                "蓉、徐卓君、谢洋、许安琦、谢雅琪、谭晰元、向攀嵩、谢思进、徐雨菲、姚钧杰、许培昇、徐子琪、詹永濠、张炜锋、林敬" +
                "圣、钟卓霖、邹乐欣、范轩毓、游智翔、郑依琪、张明哲、陈梓豪、张政、郑子莹");
        title_errorPeople.setFontColor(Color.white);
        title_errorPeople.setFontSize(8D);

        //原题信息
        XSLFTextBox textBox_question = slide.createTextBox();
        textBox_question.setAnchor(new Rectangle(30,160,500,200));
        textBox_question.setFillColor(Color.white);
        XSLFTextParagraph paragraph_question = getTextParagraph(textBox_question);
        paragraph_question.setTextAlign(TextParagraph.TextAlign.LEFT);

        XSLFTextRun text_question = getTextRun(paragraph_question);
        text_question.setText(String.format("在平面直角坐标系中，点 一定在第（ ）象限\n" +
                "  A. 第一象限     B. 第二象限\n" +
                "  C. 第三象限     D. 第四象限\n" +
                "\n" +
                "【答案】B\n" +
                "【解析】\n" +
                "∵点 它的横坐标 ，纵坐标 ， ∴符合点在第二象限的条件，故点 一定在第二象限．\n" +
                "故选：B．\n",2));
        text_question.setFontColor(Color.black);
        text_question.setFontSize(9D);

        dealErrorCase(ppt);
    }

    /**
     * 处理案例题
     * @param ppt
     */
    private static void dealErrorCase(XMLSlideShow ppt) {
        XSLFSlide slide = ppt.createSlide();

        //第一页需要案例题
        XSLFTextBox textBox_title = slide.createTextBox();
        textBox_title.setAnchor(new Rectangle(30,30,65,25));
        textBox_title.setFillColor(new Color(254, 172, 104));
        XSLFTextParagraph paragraph_title = getTextParagraph(textBox_title);
        paragraph_title.setTextAlign(TextParagraph.TextAlign.LEFT);

        XSLFTextRun title_question = getTextRun(paragraph_title);
        title_question.setText("案例题");
        title_question.setFontColor(Color.white);
        title_question.setFontSize(16D);
        //原题信息
        XSLFTextBox textBox_question = slide.createTextBox();
        textBox_question.setAnchor(new Rectangle(30,70,500,200));
        textBox_question.setFillColor(Color.white);
        XSLFTextParagraph paragraph_question = getTextParagraph(textBox_question);
        paragraph_question.setTextAlign(TextParagraph.TextAlign.LEFT);

        XSLFTextRun text_question = getTextRun(paragraph_question);
        text_question.setText(String.format("在平面直角坐标系中，点 一定在第（ ）象限\n" +
                "  A. 第一象限     B. 第二象限\n" +
                "  C. 第三象限     D. 第四象限\n" +
                "\n" +
                "【答案】B\n" +
                "【解析】\n" +
                "∵点 它的横坐标 ，纵坐标 ， ∴符合点在第二象限的条件，故点 一定在第二象限．\n" +
                "故选：B．\n",2));
        text_question.setFontColor(Color.black);
        text_question.setFontSize(9D);
    }

    /**
     * //处理第五页知识点掌握情况
     *
     * @param ppt
     * @param page
     */
    private static void dealFive(XMLSlideShow ppt, XSLFSlide page) {
        List<String> columnList = Arrays.asList("知识点名称", "知识点掌握情况", "班级得分率", "答错人数", "选题对应题号");
        List<KnpRate> list = KnpRate.getList();
        Integer rowLength = 15; //默认长度
        Integer tableNum = (list.size() + rowLength - 1) / rowLength;

        for (int tablePage = 0; tablePage < tableNum; tablePage++) {
            XSLFSlide currentPage = page;
            List<KnpRate> rowList = list.subList(tablePage * rowLength, Math.min((tablePage + 1) * rowLength, list.size()));
            if (tablePage != 0) {
                currentPage = ppt.createSlide();
                //currentPage.setFollowMasterBackground(true);
            }
            XSLFTable table = currentPage.createTable(rowList.size() + 1, columnList.size());
            table.setAnchor(new Rectangle(40, 70, 10, 10));//设置表格位置
            for (int row = 0; row <= rowList.size(); row++) {
                table.setRowHeight(row, 15);
                for (int column = 0; column < columnList.size(); column++) {
                    table.setColumnWidth(column, 120);
                    XSLFTableCell cell = table.getCell(row, column);
                    cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    if (row == 0) {//表头
                        for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                            cell.setBorderWidth(value, 2);
                            cell.setBorderColor(value, Color.white);
                            cell.setFillColor(new Color(79, 129, 189));
                        }
                        //设置单元格的字体样式
                        XSLFTextParagraph paragraph = getTextParagraph(cell);
                        paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
                        XSLFTextRun text = getTextRun(paragraph);
                        text.setFontSize(13D);
                        text.setFontColor(Color.white);
                        text.setText(columnList.get(column));
                    } else {
                        KnpRate knpRate = rowList.get(row - 1);
                        for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                            cell.setBorderWidth(value, 1);
                            cell.setBorderColor(value, Color.white);
                            cell.setFillColor(new Color(208, 216, 232));
                        }
                        //设置单元格的字体样式
                        XSLFTextParagraph paragraph = getTextParagraph(cell);
                        paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
                        XSLFTextRun text = getTextRun(paragraph);
                        text.setFontSize(8D);
                        text.setFontColor(Color.black);
                        switch (column) {
                            case 0:
                                text.setText(knpRate.getKnpName());
                                break;
                            case 1:
                                text.setText(knpRate.getStars());
                                break;
                            case 2:
                                text.setText(knpRate.getClassRate());
                                break;
                            case 3:
                                text.setText(knpRate.getErrorPeople() + "");
                                break;
                            case 4:
                                text.setText(knpRate.getQuestionNos());
                                break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 处理第四页，试题错误率分析
     *
     * @param page
     */
    private static void dealFour(XSLFSlide page) {
        List<String> rowList = Arrays.asList("题号", "答错人数", "答对人数");
        List<ErrorRate> list = ErrorRate.getList();
        Integer columnLength = 10; //默认长度
        Integer tableNum = (list.size() + columnLength - 1) / columnLength;
        for (int currentTable = 0; currentTable < tableNum; currentTable++) {
            Integer columnRealLength = Math.min(columnLength, list.size() - columnLength * currentTable);
            XSLFTable table = page.createTable(rowList.size(), columnRealLength + 1);
            table.setAnchor(new Rectangle(50, 70 + currentTable * 80, 1, 1));//设置表格位置
            for (int row = 0; row < rowList.size(); row++) {
                table.setRowHeight(row, 20);
                for (int column = 0; column <= columnRealLength; column++) {
                    table.setColumnWidth(column, 53);
                    XSLFTableCell cell = table.getCell(row, column);
                    XSLFTextParagraph paragraph = getTextParagraph(cell);
                    paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
                    XSLFTextRun text = getTextRun(paragraph);
                    text.setFontColor(Color.black);
                    if (column == 0) {
                        text.setText(rowList.get(row));
                    } else {
                        ErrorRate errorRate = list.get(columnLength * (currentTable) + column - 1);
                        if (row == 0) {
                            text.setText(errorRate.getQustionNo() + "");
                        } else if (row == 1) {
                            text.setText(errorRate.getErrorPeople() + "");
                        } else {
                            text.setText(errorRate.getErrorRate() + "");
                        }
                    }
                    if (row == 0) {
                        //题号，这里需要设置
                        for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                            cell.setBorderWidth(value, 2);
                            cell.setBorderColor(value, Color.white);
                            cell.setFillColor(new Color(79, 129, 189));
                        }
                        text.setFontColor(Color.white);
                        text.setFontSize(13D);
                        if (column == 0) {
                            text.setText(rowList.get(row));
                        } else {
                            text.setText(list.get(columnLength * (currentTable) + column - 1).getQustionNo());
                        }
                    } else {
                        for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                            cell.setBorderWidth(value, 1);
                            cell.setBorderColor(value, Color.white);
                            cell.setFillColor(new Color(208, 216, 232));
                        }
                        text.setFontSize(8D);
                    }
                }
            }
        }
    }

    /**
     * 处理第二页数据，班级情况分析表格数据
     *
     * @param page
     */
    private static void dealThree(XSLFSlide page) {
        //---- 处理班级年级分析数据
        dealTotalAnalysis(page);
        //班级大大幅进步
        dealClassUp(page);
        //班级大幅退步
        dealClassDown(page);
        //班级前五名
        dealClassTopFive(page);
        //班级后五名
        dealClassBackFive(page);
    }

    /**
     * 第三页 ---- 处理班级后五名
     *
     * @param page
     */
    private static void dealClassBackFive(XSLFSlide page) {
        List<String> cloumnList = Arrays.asList("姓名", "成绩");
        //创建一个两行四列的数据项
        XSLFTable table = page.createTable(2, 2);
        table.setAnchor(new Rectangle(551, 180, 1, 1));//设置表格位置
        table.mergeCells(0, 0, 0, 1);
        XSLFTableCell headCell = table.getCell(0, 0);
        for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
            headCell.setBorderWidth(value, 2);
            headCell.setBorderColor(value, Color.white);
            headCell.setFillColor(new Color(79, 129, 189));
        }
        XSLFTextParagraph paragraph = getTextParagraph(headCell);
        paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
        XSLFTextRun text = getTextRun(paragraph);
        text.setFontSize(13D);
        text.setFontColor(Color.white);
        text.setText("班级后五名");
        //列元素
        for (int i = 0; i < cloumnList.size(); i++) {
            XSLFTableCell tableCell = table.getCell(1, i);
            if (i == 0) {
                table.setColumnWidth(i, 40);
            } else {
                table.setColumnWidth(i, 50);
            }
            for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                tableCell.setBorderWidth(value, 2);
                tableCell.setBorderColor(value, Color.white);
                tableCell.setFillColor(new Color(208, 216, 232));
            }
            XSLFTextParagraph cellParagraph = getTextParagraph(tableCell);
            cellParagraph.setTextAlign(TextParagraph.TextAlign.CENTER);
            XSLFTextRun cellText = getTextRun(cellParagraph);
            cellText.setFontSize(8D);
            cellText.setFontColor(Color.black);
            cellText.setText(cloumnList.get(i));
        }
        table.setRowHeight(0, 10);
        table.setRowHeight(1, 1);
        //添加数据
        for (int row = 2; row < 7; row++) {
            XSLFTableRow tableRow = table.addRow();
//            table.setRowHeight(row,5);
            for (int i = 0; i < cloumnList.size(); i++) {
                XSLFTableCell tableCell = tableRow.addCell();
                tableCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                if (i == 0) {
                    table.setColumnWidth(i, 40);
                } else {
                    table.setColumnWidth(i, 50);
                }
                for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                    tableCell.setBorderWidth(value, 1);
                    tableCell.setBorderColor(value, Color.white);
                    tableCell.setFillColor(new Color(208, 216, 232));
                }
                XSLFTextParagraph cellParagraph = getTextParagraph(tableCell);
                cellParagraph.setTextAlign(TextParagraph.TextAlign.CENTER);
                XSLFTextRun cellText = getTextRun(cellParagraph);
                cellText.setFontSize(8D);
                cellText.setFontColor(Color.black);
                switch (i) {
                    case 0:
                        cellText.setText("李四");
                        break;
                    case 1:
                        cellText.setText("540");
                        break;
                }
            }
        }
    }

    /**
     * 第三页 ---- 处理班级前五名
     *
     * @param page
     */
    private static void dealClassTopFive(XSLFSlide page) {
        List<String> cloumnList = Arrays.asList("姓名", "成绩");
        //创建一个两行四列的数据项
        XSLFTable table = page.createTable(2, 2);
        table.setAnchor(new Rectangle(448, 180, 1, 1));//设置表格位置
        table.mergeCells(0, 0, 0, 1);
        XSLFTableCell headCell = table.getCell(0, 0);
        for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
            headCell.setBorderWidth(value, 2);
            headCell.setBorderColor(value, Color.white);
            headCell.setFillColor(new Color(79, 129, 189));
        }
        XSLFTextParagraph paragraph = getTextParagraph(headCell);
        paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
        XSLFTextRun text = getTextRun(paragraph);
        text.setFontSize(13D);
        text.setFontColor(Color.white);
        text.setText("班级前五名");
        //列元素
        for (int i = 0; i < cloumnList.size(); i++) {
            XSLFTableCell tableCell = table.getCell(1, i);
            if (i == 0) {
                table.setColumnWidth(i, 40);
            } else {
                table.setColumnWidth(i, 50);
            }
            for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                tableCell.setBorderWidth(value, 2);
                tableCell.setBorderColor(value, Color.white);
                tableCell.setFillColor(new Color(208, 216, 232));
            }
            XSLFTextParagraph cellParagraph = getTextParagraph(tableCell);
            cellParagraph.setTextAlign(TextParagraph.TextAlign.CENTER);
            XSLFTextRun cellText = getTextRun(cellParagraph);
            cellText.setFontSize(8D);
            cellText.setFontColor(Color.black);
            cellText.setText(cloumnList.get(i));
        }
        table.setRowHeight(0, 10);
        table.setRowHeight(1, 1);
        //添加数据
        for (int row = 2; row < 7; row++) {
            XSLFTableRow tableRow = table.addRow();
//            table.setRowHeight(row,5);
            for (int i = 0; i < cloumnList.size(); i++) {
                XSLFTableCell tableCell = tableRow.addCell();
                tableCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                if (i == 0) {
                    table.setColumnWidth(i, 40);
                } else {
                    table.setColumnWidth(i, 50);
                }
                for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                    tableCell.setBorderWidth(value, 1);
                    tableCell.setBorderColor(value, Color.white);
                    tableCell.setFillColor(new Color(208, 216, 232));
                }
                XSLFTextParagraph cellParagraph = getTextParagraph(tableCell);
                cellParagraph.setTextAlign(TextParagraph.TextAlign.CENTER);
                XSLFTextRun cellText = getTextRun(cellParagraph);
                cellText.setFontSize(8D);
                cellText.setFontColor(Color.black);
                switch (i) {
                    case 0:
                        cellText.setText("李四");
                        break;
                    case 1:
                        cellText.setText("540");
                        break;
                }
            }
        }
    }

    /**
     * 第三页 ---- 处理班级大大幅退步
     *
     * @param page
     */
    private static void dealClassDown(XSLFSlide page) {
        List<String> cloumnList = Arrays.asList("姓名", "班级排名", "排名下降", "年级排名");
        //创建一个两行四列的数据项
        XSLFTable table = page.createTable(2, 4);
        table.setAnchor(new Rectangle(243, 180, 1, 1));//设置表格位置
        table.mergeCells(0, 0, 0, 3);
        XSLFTableCell headCell = table.getCell(0, 0);
        for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
            headCell.setBorderWidth(value, 2);
            headCell.setBorderColor(value, Color.white);
            headCell.setFillColor(new Color(79, 129, 189));
        }
        XSLFTextParagraph paragraph = getTextParagraph(headCell);
        paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
        XSLFTextRun text = getTextRun(paragraph);
        text.setFontSize(13D);
        text.setFontColor(Color.white);
        text.setText("班级大幅退步");
        //列元素
        for (int i = 0; i < cloumnList.size(); i++) {
            XSLFTableCell tableCell = table.getCell(1, i);
            if (i == 0) {
                table.setColumnWidth(i, 40);
            } else {
                table.setColumnWidth(i, 50);
            }
            for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                tableCell.setBorderWidth(value, 2);
                tableCell.setBorderColor(value, Color.white);
                tableCell.setFillColor(new Color(208, 216, 232));
            }
            XSLFTextParagraph cellParagraph = getTextParagraph(tableCell);
            cellParagraph.setTextAlign(TextParagraph.TextAlign.CENTER);
            XSLFTextRun cellText = getTextRun(cellParagraph);
            cellText.setFontSize(8D);
            cellText.setFontColor(Color.black);
            cellText.setText(cloumnList.get(i));
        }
        table.setRowHeight(0, 10);
        table.setRowHeight(1, 1);
        //添加数据
        for (int row = 2; row < 7; row++) {
            XSLFTableRow tableRow = table.addRow();
//            table.setRowHeight(row,5);
            for (int i = 0; i < cloumnList.size(); i++) {
                XSLFTableCell tableCell = tableRow.addCell();
                tableCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                if (i == 0) {
                    table.setColumnWidth(i, 40);
                } else {
                    table.setColumnWidth(i, 50);
                }
                for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                    tableCell.setBorderWidth(value, 1);
                    tableCell.setBorderColor(value, Color.white);
                    tableCell.setFillColor(new Color(208, 216, 232));
                }
                XSLFTextParagraph cellParagraph = getTextParagraph(tableCell);
                cellParagraph.setTextAlign(TextParagraph.TextAlign.CENTER);
                XSLFTextRun cellText = getTextRun(cellParagraph);
                cellText.setFontSize(8D);
                cellText.setFontColor(Color.black);
                switch (i) {
                    case 0:
                        cellText.setText("李四");
                        break;
                    case 1:
                        cellText.setText("1");
                        break;
                    case 2:
                        cellText.setText("23");
                        break;
                    case 3:
                        cellText.setText("5");
                        break;
                }
            }
        }
    }

    /**
     * 第三页 ---- 处理班级大大幅进步
     *
     * @param page
     */
    private static void dealClassUp(XSLFSlide page) {
        List<String> cloumnList = Arrays.asList("姓名", "班级排名", "排名提升", "年级排名");
        //创建一个两行四列的数据项
        XSLFTable table = page.createTable(2, 4);
        table.setAnchor(new Rectangle(40, 180, 1, 1));//设置表格位置
        table.mergeCells(0, 0, 0, 3);
        XSLFTableCell headCell = table.getCell(0, 0);
        for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
            headCell.setBorderWidth(value, 2);
            headCell.setBorderColor(value, Color.white);
            headCell.setFillColor(new Color(79, 129, 189));
        }
        XSLFTextParagraph paragraph = getTextParagraph(headCell);
        paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
        XSLFTextRun text = getTextRun(paragraph);
        text.setFontSize(13D);
        text.setFontColor(Color.white);
        text.setText("班级大幅进步");
        //列元素
        for (int i = 0; i < cloumnList.size(); i++) {
            XSLFTableCell tableCell = table.getCell(1, i);
            if (i == 0) {
                table.setColumnWidth(i, 40);
            } else {
                table.setColumnWidth(i, 50);
            }
            for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                tableCell.setBorderWidth(value, 2);
                tableCell.setBorderColor(value, Color.white);
                tableCell.setFillColor(new Color(208, 216, 232));
            }
            XSLFTextParagraph cellParagraph = getTextParagraph(tableCell);
            cellParagraph.setTextAlign(TextParagraph.TextAlign.CENTER);
            XSLFTextRun cellText = getTextRun(cellParagraph);
            cellText.setFontSize(8D);
            cellText.setFontColor(Color.black);
            cellText.setText(cloumnList.get(i));
        }
        table.setRowHeight(0, 10);
        table.setRowHeight(1, 1);
        //添加数据
        for (int row = 2; row < 7; row++) {
            XSLFTableRow tableRow = table.addRow();
//            table.setRowHeight(row,5);
            for (int i = 0; i < cloumnList.size(); i++) {
                XSLFTableCell tableCell = tableRow.addCell();
                tableCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                if (i == 0) {
                    table.setColumnWidth(i, 40);
                } else {
                    table.setColumnWidth(i, 50);
                }
                for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                    tableCell.setBorderWidth(value, 1);
                    tableCell.setBorderColor(value, Color.white);
                    tableCell.setFillColor(new Color(208, 216, 232));
                }
                XSLFTextParagraph cellParagraph = getTextParagraph(tableCell);
                cellParagraph.setTextAlign(TextParagraph.TextAlign.CENTER);
                XSLFTextRun cellText = getTextRun(cellParagraph);
                cellText.setFontSize(8D);
                cellText.setFontColor(Color.black);
                switch (i) {
                    case 0:
                        cellText.setText("张三");
                        break;
                    case 1:
                        cellText.setText("1");
                        break;
                    case 2:
                        cellText.setText("23");
                        break;
                    case 3:
                        cellText.setText("5");
                        break;
                }
            }
        }
    }

    /**
     * 获取文本域文本
     *
     * @param paragraph
     * @return
     */
    private static XSLFTextRun getTextRun(XSLFTextParagraph paragraph) {
        return paragraph.addNewTextRun();
    }

    /**
     * 获取或新增文本域
     *
     * @param cell
     * @return
     */
    private static XSLFTextParagraph getTextParagraph(XSLFTextShape cell) {
        cell.clearText();
        return cell.getTextParagraphs().isEmpty() ? cell.addNewTextParagraph() : cell.getTextParagraphs().get(0);
    }

    /**
     * 第三页 ---- 处理班级年级分析数据
     *
     * @param page
     */
    private static void dealTotalAnalysis(XSLFSlide page) {
        List<String> rowList = Arrays.asList("", "班级", "年级");
        List<String> cloumnList = Arrays.asList("", "排名", "平均分", "得分率", "优秀率", "及格率", "考试人数", "缺考人数");

        XSLFTable table = page.createTable(3, 8);
        table.setAnchor(new Rectangle(40, 70, 20, 20));//设置表格位置

        for (int i = 0; i < rowList.size(); i++) {
            table.setRowHeight(i, 15);
            for (int j = 0; j < cloumnList.size(); j++) {
                table.setColumnWidth(j, 75);
                XSLFTableCell cell = table.getCell(i, j);
                cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                if (i == 0 || j == 0) {//表头
                    for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                        cell.setBorderWidth(value, 2);
                        cell.setBorderColor(value, Color.white);
                        cell.setFillColor(new Color(79, 129, 189));
                    }
                    //设置单元格的字体样式
                    XSLFTextParagraph paragraph = getTextParagraph(cell);
                    paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
                    XSLFTextRun text = getTextRun(paragraph);
                    text.setFontSize(13D);
                    text.setFontColor(Color.white);
                } else {
                    for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                        cell.setBorderWidth(value, 1);
                        cell.setBorderColor(value, Color.white);
                        cell.setFillColor(new Color(208, 216, 232));
                    }
                    //设置单元格的字体样式
                    XSLFTextParagraph paragraph = getTextParagraph(cell);
                    paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
                    XSLFTextRun text = getTextRun(paragraph);
                    text.setFontSize(10D);
                    text.setFontColor(Color.black);
                }
            }
        }
        //列元素
        for (int i = 0; i < rowList.size(); i++) {
            XSLFTableCell tableCell = table.getCell(i, 0);
            tableCell.getTextParagraphs().get(0).getTextRuns().get(0).setText(rowList.get(i));
        }
        //行元素
        for (int i = 1; i < cloumnList.size(); i++) {
            XSLFTableCell tableCell = table.getCell(0, i);
            tableCell.getTextParagraphs().get(0).getTextRuns().get(0).setText(cloumnList.get(i));
        }
        //班级数据
        table.getCell(1, 1).getTextParagraphs().get(0).getTextRuns().get(0).setText("34");
        table.getCell(1, 2).getTextParagraphs().get(0).getTextRuns().get(0).setText("85");
        table.getCell(1, 3).getTextParagraphs().get(0).getTextRuns().get(0).setText("30%");
        table.getCell(1, 4).getTextParagraphs().get(0).getTextRuns().get(0).setText("20%");
        table.getCell(1, 5).getTextParagraphs().get(0).getTextRuns().get(0).setText("40%");
        table.getCell(1, 6).getTextParagraphs().get(0).getTextRuns().get(0).setText("58");
        table.getCell(1, 7).getTextParagraphs().get(0).getTextRuns().get(0).setText("5");
        //年级数据
        table.getCell(2, 1).getTextParagraphs().get(0).getTextRuns().get(0).setText("34");
        table.getCell(2, 2).getTextParagraphs().get(0).getTextRuns().get(0).setText("85");
        table.getCell(2, 3).getTextParagraphs().get(0).getTextRuns().get(0).setText("30%");
        table.getCell(2, 4).getTextParagraphs().get(0).getTextRuns().get(0).setText("20%");
        table.getCell(2, 5).getTextParagraphs().get(0).getTextRuns().get(0).setText("40%");
        table.getCell(2, 6).getTextParagraphs().get(0).getTextRuns().get(0).setText("58");
        table.getCell(2, 7).getTextParagraphs().get(0).getTextRuns().get(0).setText("5");
    }

    /**
     * 处理第一页数据
     *
     * @param page
     * @param examName
     * @param className
     */
    private static void dealFirst(XSLFSlide page, String examName, String className) {
        XSLFTextBox textBox = page.createTextBox();
        textBox.setAnchor(new Rectangle(155, 125, 475, 200));
        XSLFTextParagraph paragraph = getTextParagraph(textBox);
        paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
        //考试名称
        paragraph.addLineBreak();
        XSLFTextRun examNameText = getTextRun(paragraph);
        examNameText.setText(examName);
        examNameText.setFontColor(Color.white);
        examNameText.setFontSize(20D);
        //班级名称
        paragraph.addLineBreak();
        paragraph.addLineBreak();
        XSLFTextRun classNameText = getTextRun(paragraph);
        classNameText.setText(className);
        classNameText.setFontColor(Color.yellow);
        classNameText.setFontSize(15D);
    }

}

