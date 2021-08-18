package com.hsm.java.fx;

/**
 * @Classname DirectoryChooserExample
 * @Description TODO
 * @Date 2021/8/18 20:02
 * @Created by huangsm
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class DirectoryChooserExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        DirectoryChooser directoryChooser = new DirectoryChooser();
        Button button = new Button("Select Directory");
        button.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            System.out.println(selectedDirectory.getAbsolutePath());
        });


        VBox vBox = new VBox(button);
        //HBox hBox = new HBox(button1, button2);
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
