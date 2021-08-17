package com.hsm.java.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileInputStream;


public class MenuButtonExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ImageView Experiment 1");


        MenuItem menuItem1 = new MenuItem("Option 1");
        MenuItem menuItem2 = new MenuItem("Option 2");
        MenuItem menuItem3 = new MenuItem("Option 3");

        FileInputStream input = new FileInputStream("C:\\Users\\Administrator\\Desktop\\images\\javafx-node-coordinate-system.png");
        Image image = new Image(input,30,30,false,false);
        ImageView imageView = new ImageView(image);

        MenuButton menuButton = new MenuButton("Options", imageView, menuItem1, menuItem2, menuItem3);

        HBox hbox = new HBox(menuButton);

        Scene scene = new Scene(hbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}