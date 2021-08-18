package com.hsm.java.fx;

/**
 * @Classname PaneExample
 * @Description TODO
 * @Date 2021/8/18 20:45
 * @Created by huangsm
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PaneExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        Pane  pane  = new Pane();

        pane.getChildren().add(new Label("Hello Pane"));

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}