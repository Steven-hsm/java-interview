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
import javafx.stage.Stage;

import java.util.List;


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
        HBox hbox1 = new HBox(box1_1,separator,lineChart1_2);


        GridPane gridPane2_1 = get2_1();
        HBox hbox2 = new HBox(gridPane2_1);
        hbox2.setStyle("-fx-background-color:#BFDCC0");

        HBox hbox3 = new HBox();
        VBox vBox = new VBox(hbox1,hbox2,hbox3);

        Scene Scene = new Scene(vBox);
        stage.setScene(Scene);
        stage.setHeight(750);
        stage.setWidth(1000);
        stage.show();
    }

    private GridPane get2_1() {
        GridPane gridPane = new GridPane();
        gridPane.setPrefHeight(280);
        gridPane.setPrefWidth(900);

        Label label = new Label("ColdNTc");
        TextField text = new TextField("");
        text.setPrefWidth(10);
        HBox vBox = new HBox(label, text);
        vBox.setSpacing(5);
        gridPane.add(vBox,0,0);

        return gridPane;
    }

    private LineChart get1_2() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Minutes");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Template");
        LineChart lineChart = new LineChart(xAxis, yAxis);
        lineChart.setPrefHeight(250);
        lineChart.setPrefWidth(750);
        lineChart.setTitleSide(Side.TOP);
        lineChart.setTitle("title");
        lineChart.setLegendVisible(false);

        XYChart.Series dataSeries1 = new XYChart.Series();

        dataSeries1.getData().add(new XYChart.Data( 1, 567));
        dataSeries1.getData().add(new XYChart.Data( 5, 612));
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
        gridPane.add(com_setting,0,0);
        gridPane.add(com,0,1);
        gridPane.add(comboBox,1,1);
        gridPane.add(com2,0,2);
        gridPane.add(comboBox2,1,2);
        gridPane.add(com3,0,3);

        Button button1 = new Button("starting parse");
        button1.setPrefWidth(130);
        Button button2 = new Button("Pause");
        button2.setPrefWidth(130);
        Button button3 = new Button("Clear");
        button3.setPrefWidth(130);

        VBox box1_1 = new VBox(gridPane,button1,button2,button3);
        box1_1.setAlignment(Pos.CENTER);
        box1_1.setPrefWidth(200);
        box1_1.setPrefHeight(220);
        box1_1.setSpacing(10);
        return box1_1;
    }
}
