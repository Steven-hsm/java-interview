package com.hsm.java.fx;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HyperlinkExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        Hyperlink link = new Hyperlink("Click Me!");
        link.setOnAction(e ->{
            System.out.println("click me");
        });


        VBox vBox = new VBox(link);
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}