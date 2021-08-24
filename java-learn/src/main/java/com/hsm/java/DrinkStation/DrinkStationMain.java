package com.hsm.java.DrinkStation;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class DrinkStationMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image("file:img.png")); //设置图标
        stage.setTitle("DrinkStation Rev2.1-2017-11-28");//设置标题
        //网格布局
        VBox box1_1 = get1_1();
        LineChart lineChart1_2 = get1_2();
        Separator separator = new Separator(Orientation.VERTICAL);
        HBox hbox1 = new HBox(box1_1, separator, lineChart1_2);
        hbox1.setMaxWidth(1450);

        HBox displayData = get2_1();

        HBox hbox2_2 = get2_2();
        VBox hbox2 = new VBox(displayData, hbox2_2);
        hbox2.setSpacing(10);
        hbox2.setStyle("-fx-background-color:#BFDCC0");
        hbox2.setPrefHeight(310);
        hbox2.setMaxWidth(1450);

        HBox hbox3 = new HBox();
        hbox3.setMaxWidth(1450);
        VBox vBox = new VBox(hbox1, hbox2, hbox3);

        Scene Scene = new Scene(vBox);
        stage.setScene(Scene);
        stage.setHeight(800);
        stage.setWidth(1500);
        stage.show();
    }

    private HBox get2_2() {
        VBox vbox2_2_1 = get2_2_1();
        VBox vbox2_2_2 = get2_2_2();
        VBox vbox2_2_3 = get2_2_3();
        VBox vbox2_2_4 = get2_2_4();

        HBox hBox = new HBox(vbox2_2_1, vbox2_2_2, vbox2_2_3,vbox2_2_4);
        hBox.setSpacing(30);
        return hBox;
    }

    private VBox get2_2_4() {
        Label label1 = new Label("Y_Button_IN:");
        label1.setPrefWidth(150);
        label1.setPrefHeight(15);
        label1.setTextFill(Color.BLUE);
        TextField text1 = new TextField("");
        text1.setPrefWidth(80);
        HBox hBox1 = new HBox(label1, text1);
        hBox1.setSpacing(20);

        Label label2 = new Label("W_Button_IN");
        label2.setPrefWidth(150);
        label2.setPrefHeight(15);
        label2.setTextFill(Color.BLUE);
        TextField text2 = new TextField("");
        text2.setPrefWidth(80);
        HBox hBox2 = new HBox(label2, text2);
        hBox2.setSpacing(20);

        Label label3 = new Label("B_Button_IN");
        label3.setPrefWidth(150);
        label3.setPrefHeight(15);
        label3.setTextFill(Color.BLUE);
        TextField text3 = new TextField("");
        text3.setPrefWidth(80);
        HBox hBox3 = new HBox(label3, text3);
        hBox3.setSpacing(20);

        Label label4 = new Label("G_Button_IN");
        label4.setPrefWidth(150);
        label4.setPrefHeight(15);
        label4.setTextFill(Color.BLUE);
        TextField text4 = new TextField("");
        text4.setPrefWidth(80);
        HBox hBox4 = new HBox(label4, text4);
        hBox4.setSpacing(20);

        Label label5 = new Label("R_Button_IN");
        label5.setPrefWidth(150);
        label5.setPrefHeight(15);
        label5.setTextFill(Color.BLUE);
        TextField text5 = new TextField("");
        text5.setPrefWidth(80);
        HBox hBox5 = new HBox(label5, text5);
        hBox5.setSpacing(20);

        Label label6 = new Label("Filter_Button_IN");
        label6.setPrefWidth(150);
        label6.setPrefHeight(15);
        label6.setTextFill(Color.BLUE);
        TextField text6 = new TextField("");
        text6.setPrefWidth(80);
        HBox hBox6 = new HBox(label6, text6);
        hBox6.setSpacing(20);

        Label label7 = new Label("Debug_Button_IN");
        label7.setPrefWidth(150);
        label7.setPrefHeight(15);
        label7.setTextFill(Color.BLUE);
        TextField text7 = new TextField("");
        text7.setPrefWidth(80);
        HBox hBox7 = new HBox(label7, text7);
        hBox7.setSpacing(20);

        Label label8 = new Label("Cold_level_switch_up");
        label8.setPrefWidth(150);
        label8.setPrefHeight(15);
        label8.setTextFill(Color.BLUE);
        TextField text8 = new TextField("");
        text8.setPrefWidth(80);
        HBox hBox8 = new HBox(label8, text8);
        hBox8.setSpacing(20);

        Label label9 = new Label("Cold_level_swtich_down");
        label9.setPrefWidth(150);
        label9.setPrefHeight(15);
        label9.setTextFill(Color.BLUE);
        TextField text9 = new TextField("");
        text9.setPrefWidth(80);
        HBox hBox9 = new HBox(label9, text9);
        hBox8.setSpacing(20);

        VBox vBox = new VBox(hBox1, hBox2, hBox3, hBox4, hBox5, hBox6, hBox7,hBox8,hBox9);
        vBox.setSpacing(5);
        return vBox;
    }

    private VBox get2_2_3() {
        Label label1 = new Label("USB TXT OUTPUT:");
        label1.setPrefWidth(150);
        label1.setTextFill(Color.RED);
        HBox hBox1 = new HBox(label1);

        Label label2 = new Label("Sparkling Water Flow:");
        label2.setPrefWidth(150);
        label2.setTextFill(Color.BLUE);
        TextField text2 = new TextField("");
        text2.setPrefWidth(80);
        HBox hBox2 = new HBox(label2, text2);
        hBox2.setSpacing(20);

        Label label3 = new Label("Chilled Water Flow:");
        label3.setPrefWidth(150);
        label3.setTextFill(Color.BLUE);
        TextField text3 = new TextField("");
        text3.setPrefWidth(80);
        HBox hBox3 = new HBox(label3, text3);
        hBox3.setSpacing(20);

        Label label4 = new Label("Alkaling Flow:");
        label4.setPrefWidth(150);
        label4.setTextFill(Color.BLUE);
        TextField text4 = new TextField("");
        text4.setPrefWidth(80);
        HBox hBox4 = new HBox(label4, text4);
        hBox4.setSpacing(20);

        Label label5 = new Label("Hot Water Flow:");
        label5.setPrefWidth(150);
        label5.setTextFill(Color.BLUE);
        TextField text5 = new TextField("");
        text5.setPrefWidth(80);
        HBox hBox5 = new HBox(label5, text5);
        hBox5.setSpacing(20);

        Label label6 = new Label("Date:");
        label6.setPrefWidth(150);
        label6.setTextFill(Color.BLUE);
        TextField text6 = new TextField("");
        text6.setPrefWidth(80);
        HBox hBox6 = new HBox(label6, text6);
        hBox6.setSpacing(20);

        VBox vBox = new VBox(hBox1, hBox2, hBox3, hBox4, hBox5, hBox6);
        vBox.setSpacing(5);
        return vBox;
    }

    private VBox get2_2_2() {
        Label label1 = new Label("Hot Tank Power:");
        label1.setPrefWidth(150);
        label1.setTextFill(Color.BLUE);
        TextField text1 = new TextField("");
        text1.setPrefWidth(80);
        HBox hBox1 = new HBox(label1, text1);
        hBox1.setSpacing(20);

        Label label2 = new Label("Agitator Power:");
        label2.setPrefWidth(150);
        label2.setTextFill(Color.BLUE);
        TextField text2 = new TextField("");
        text2.setPrefWidth(80);
        HBox hBox2 = new HBox(label2, text2);
        hBox2.setSpacing(20);

        Label label3 = new Label("Water Pump Power:");
        label3.setPrefWidth(150);
        label3.setTextFill(Color.BLUE);
        TextField text3 = new TextField("");
        text3.setPrefWidth(80);
        HBox hBox3 = new HBox(label3, text3);
        hBox3.setSpacing(20);

        Label label4 = new Label("Backup PWM output1:");
        label4.setPrefWidth(150);
        label4.setTextFill(Color.BLUE);
        TextField text4 = new TextField("");
        text4.setPrefWidth(80);
        HBox hBox4 = new HBox(label4, text4);
        hBox4.setSpacing(20);

        Label label5 = new Label("Backup PWM output2:");
        label5.setPrefWidth(150);
        label5.setTextFill(Color.BLUE);
        TextField text5 = new TextField("");
        text5.setPrefWidth(80);
        HBox hBox5 = new HBox(label5, text5);
        hBox5.setSpacing(20);

        Label label6 = new Label("Water PWM PWM:");
        label6.setPrefWidth(150);
        label6.setTextFill(Color.BLUE);
        TextField text6 = new TextField("");
        text6.setPrefWidth(80);
        HBox hBox6 = new HBox(label6, text6);
        hBox6.setSpacing(20);

        Label label7 = new Label("Agitator PWM:");
        label7.setPrefWidth(150);
        label7.setTextFill(Color.BLUE);
        TextField text7 = new TextField("");
        text7.setPrefWidth(80);
        HBox hBox7 = new HBox(label7, text7);
        hBox7.setSpacing(20);

        Label label8 = new Label("work_mode");
        label8.setPrefWidth(150);
        label8.setTextFill(Color.BLUE);
        TextField text8 = new TextField("");
        text8.setPrefWidth(80);
        HBox hBox8 = new HBox(label8, text8);
        hBox8.setSpacing(20);

        VBox vBox = new VBox(hBox1, hBox2, hBox3, hBox4, hBox5, hBox6, hBox7,hBox8);
        vBox.setSpacing(5);
        return vBox;
    }

    private VBox get2_2_1() {
        Label label1 = new Label("  Cold NTC:");
        label1.setPrefWidth(150);
        label1.setTextFill(Color.BLUE);
        TextField text1 = new TextField("");
        text1.setPrefWidth(80);
        HBox hBox1 = new HBox(label1, text1);
        hBox1.setSpacing(20);

        Label label2 = new Label("  Hot tank NTC:");
        label2.setPrefWidth(150);
        label2.setTextFill(Color.BLUE);
        TextField text2 = new TextField("");
        text2.setPrefWidth(80);
        HBox hBox2 = new HBox(label2, text2);
        hBox2.setSpacing(20);

        Label label3 = new Label("  NTC3:");
        label3.setPrefWidth(150);
        label3.setTextFill(Color.BLUE);
        TextField text3 = new TextField("");
        text3.setPrefWidth(80);
        HBox hBox3 = new HBox(label3, text3);
        hBox3.setSpacing(20);

        Label label4 = new Label("  Ice dtetctor ADC:");
        label4.setPrefWidth(150);
        label4.setTextFill(Color.BLUE);
        TextField text4 = new TextField("");
        text4.setPrefWidth(80);
        HBox hBox4 = new HBox(label4, text4);
        hBox4.setSpacing(20);

        Label label5 = new Label("  Hot Tank Level ADC:");
        label5.setPrefWidth(150);
        label5.setTextFill(Color.BLUE);
        TextField text5 = new TextField("");
        text5.setPrefWidth(80);
        HBox hBox5 = new HBox(label5, text5);
        hBox5.setSpacing(20);

        Label label6 = new Label("  ToTal Flow:");
        label6.setPrefWidth(150);
        label6.setTextFill(Color.BLUE);
        TextField text6 = new TextField("");
        text6.setPrefWidth(80);
        HBox hBox6 = new HBox(label6, text6);
        hBox6.setSpacing(20);

        Label label7 = new Label("  AC Frequency:");
        label7.setPrefWidth(150);
        label7.setTextFill(Color.BLUE);
        TextField text7 = new TextField("");
        text7.setPrefWidth(80);
        HBox hBox7 = new HBox(label7, text7);
        hBox7.setSpacing(20);

        Label label8 = new Label("  program_mode:");
        label8.setPrefWidth(150);
        label8.setTextFill(Color.BLUE);
        TextField text8 = new TextField("");
        text8.setPrefWidth(80);
        HBox hBox8 = new HBox(label8, text8);
        hBox8.setSpacing(20);

        VBox vBox = new VBox(hBox1, hBox2, hBox3, hBox4, hBox5, hBox6, hBox7,hBox8);
        vBox.setSpacing(5);
        return vBox;
    }

    private HBox get2_1() {
        Label displayDataLable = new Label("Display data");
        Rectangle rectangle1 = new Rectangle();
        rectangle1.setWidth(1355);
        rectangle1.setHeight(8);
        rectangle1.setStroke(Color.TRANSPARENT);
        rectangle1.setFill(Color.valueOf("#BFDCC0"));

        Rectangle rectangle2 = new Rectangle();
        rectangle2.setWidth(1355);
        rectangle2.setHeight(2);
        rectangle2.setStroke(Color.TRANSPARENT);
        rectangle2.setFill(Color.WHITE);
        VBox white_line = new VBox(rectangle1, rectangle2);
        HBox displayData = new HBox(displayDataLable, white_line);
        displayData.setSpacing(2);
        return displayData;
    }

    private LineChart get1_2() {
        NumberAxis xAxis = new NumberAxis();
//        xAxis.setLabel("Minutes");
        NumberAxis yAxis = new NumberAxis();
//        yAxis.setLabel("Template");

        LineChart lineChart = new LineChart(xAxis, yAxis);
        lineChart.setPrefHeight(200);
        lineChart.setPrefWidth(1150);
        lineChart.setTitleSide(Side.TOP);
        lineChart.setTitle("title");
        lineChart.setLegendVisible(false);

        XYChart.Series dataSeries1 = new XYChart.Series();

        dataSeries1.getData().add(new XYChart.Data(1, 567));
        dataSeries1.getData().add(new XYChart.Data(5, 612));
        dataSeries1.getData().add(new XYChart.Data(10, 800));
        dataSeries1.getData().add(new XYChart.Data(20, 780));
        dataSeries1.getData().add(new XYChart.Data(40, 810));
        dataSeries1.getData().add(new XYChart.Data(80, 850));

        lineChart.getData().add(dataSeries1);

        return lineChart;
    }

    private VBox get1_1() {
        Label com_setting = new Label("COM Setting");

        Label com = new Label("Com");
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().add("Com1");
        comboBox.getItems().add("Com2");
        comboBox.getItems().add("Com3");

        Label com2 = new Label("Boarding");
        TextField comboBox2 = new TextField("115300");
        comboBox2.setPrefWidth(40);

        Label com3 = new Label("Status");

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.add(com_setting, 0, 0);
        gridPane.add(com, 0, 1);
        gridPane.add(comboBox, 1, 1);
        gridPane.add(com2, 0, 2);
        gridPane.add(comboBox2, 1, 2);
        gridPane.add(com3, 0, 3);

        Button button1 = new Button("starting parse");
        button1.setPrefWidth(130);
        Button button2 = new Button("Pause");
        button2.setPrefWidth(130);
        Button button3 = new Button("Clear");
        button3.setPrefWidth(130);
        Label space = new Label();
        space.setPrefHeight(10);
        VBox box1_1 = new VBox(gridPane, button1, button2, button3, space);
        box1_1.setAlignment(Pos.CENTER);
        box1_1.setPrefWidth(300);
        box1_1.setPrefHeight(200);
        box1_1.setSpacing(10);
        return box1_1;
    }
}

