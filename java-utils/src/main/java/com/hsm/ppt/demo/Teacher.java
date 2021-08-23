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

        ppt.write(new FileOutputStream("教师讲义.pptx"));
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
                            cell.setFillColor(Color.blue);
                        }
                        //设置单元格的字体样式
                        XSLFTextParagraph paragraph = getTextParagraph(cell);
                        paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
                        XSLFTextRun text = getTextRun(paragraph);
                        text.setFontSize(13D);
                        text.setFontColor(Color.black);
                        text.setText(columnList.get(column));
                    } else {
                        KnpRate knpRate = rowList.get(row - 1);
                        for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                            cell.setBorderWidth(value, 1);
                            cell.setBorderColor(value, Color.white);
                            cell.setFillColor(Color.PINK);
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
                            cell.setFillColor(Color.blue);
                        }
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
                            cell.setFillColor(Color.PINK);
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
            headCell.setFillColor(Color.blue);
        }
        XSLFTextParagraph paragraph = getTextParagraph(headCell);
        paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
        XSLFTextRun text = getTextRun(paragraph);
        text.setFontSize(13D);
        text.setFontColor(Color.black);
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
                tableCell.setFillColor(Color.PINK);
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
                    tableCell.setFillColor(Color.PINK);
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
            headCell.setFillColor(Color.blue);
        }
        XSLFTextParagraph paragraph = getTextParagraph(headCell);
        paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
        XSLFTextRun text = getTextRun(paragraph);
        text.setFontSize(13D);
        text.setFontColor(Color.black);
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
                tableCell.setFillColor(Color.PINK);
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
                    tableCell.setFillColor(Color.PINK);
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
            headCell.setFillColor(Color.blue);
        }
        XSLFTextParagraph paragraph = getTextParagraph(headCell);
        paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
        XSLFTextRun text = getTextRun(paragraph);
        text.setFontSize(13D);
        text.setFontColor(Color.black);
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
                tableCell.setFillColor(Color.PINK);
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
                    tableCell.setFillColor(Color.PINK);
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
            headCell.setFillColor(Color.blue);
        }
        XSLFTextParagraph paragraph = getTextParagraph(headCell);
        paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
        XSLFTextRun text = getTextRun(paragraph);
        text.setFontSize(13D);
        text.setFontColor(Color.black);
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
                tableCell.setFillColor(Color.PINK);
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
                    tableCell.setFillColor(Color.PINK);
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
                        cell.setFillColor(Color.blue);
                    }
                    //设置单元格的字体样式
                    XSLFTextParagraph paragraph = getTextParagraph(cell);
                    paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
                    XSLFTextRun text = getTextRun(paragraph);
                    text.setFontSize(13D);
                    text.setFontColor(Color.black);
                } else {
                    for (TableCell.BorderEdge value : TableCell.BorderEdge.values()) {
                        cell.setBorderWidth(value, 1);
                        cell.setBorderColor(value, Color.white);
                        cell.setFillColor(Color.PINK);
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

