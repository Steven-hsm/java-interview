package com.hsm.java.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class LabelExperiments extends Application  {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("HBox Experiment 1");

        FileInputStream input = new FileInputStream("C:\\Users\\Administrator\\Desktop\\images\\javafx-node-coordinate-system-2.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);


        Label label = new Label("My Label", imageView);

        Scene scene = new Scene(label, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}