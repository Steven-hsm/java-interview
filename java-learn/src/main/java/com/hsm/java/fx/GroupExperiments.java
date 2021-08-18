package com.hsm.java.fx;

/**
 * @Classname GroupExperiments
 * @Description TODO
 * @Date 2021/8/18 20:35
 * @Created by huangsm
 */
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class GroupExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("HBox Experiment 1");

        Button button1 = new Button("Button Number 1");
        Button button2 = new Button("Button 2");

        Group group = new Group();

        group.getChildren().add(button1);
        group.getChildren().add(button2);

        Scene scene = new Scene(group, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}