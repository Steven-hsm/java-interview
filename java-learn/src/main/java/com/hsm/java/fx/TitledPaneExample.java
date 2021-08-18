package com.hsm.java.fx;

/**
 * @Classname TitledPaneExample
 * @Description TODO
 * @Date 2021/8/18 20:12
 * @Created by huangsm
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TitledPaneExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("The content inside the TitledPane");
        TitledPane titledPane = new TitledPane("The Title", label);

        Scene scene = new Scene(new VBox(titledPane));
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}