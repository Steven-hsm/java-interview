package com.hsm.java.fx;

/**
 * @Classname SplitPaneExample
 * @Description TODO
 * @Date 2021/8/18 20:21
 * @Created by huangsm
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SplitPaneExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        SplitPane splitPane = new SplitPane();

        VBox leftControl  = new VBox(new Label("Left Control"));
        VBox rightControl = new VBox(new Label("Right Control"));

        splitPane.getItems().addAll(leftControl, rightControl);

        Scene scene = new Scene(splitPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX App");

        primaryStage.show();
    }
}