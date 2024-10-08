package com.hsm.java.fx;

/**
 * @Classname HtmlEditorExample
 * @Description TODO
 * @Date 2021/8/19 20:54
 * @Created by huangsm
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class HtmlEditorExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        HTMLEditor htmlEditor = new HTMLEditor();

        VBox vBox = new VBox(htmlEditor);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX App");

        primaryStage.show();
    }
}