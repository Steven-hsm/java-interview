package com.hsm.java.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SliderExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        Slider slider = new Slider(0, 100, 0);
        slider.setMajorTickUnit(8);
        slider.setMinorTickCount(1);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
        slider.setShowTickMarks(true);
        Button button = new Button("获取Slider值");
        TextField textField = new TextField("0");
        VBox vBox = new VBox(slider,button,textField);
        button.setOnAction(event -> {
            double value = slider.getValue();
            textField.setText(value+"");
        });
        Scene scene = new Scene(vBox, 960, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}