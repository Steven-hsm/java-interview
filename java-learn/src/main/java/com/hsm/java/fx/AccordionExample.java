package com.hsm.java.fx;

/**
 * @Classname AccordionExample
 * @Description TODO
 * @Date 2021/8/18 20:18
 * @Created by huangsm
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccordionExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        Accordion accordion = new Accordion();

        TitledPane pane1 = new TitledPane("Boats" , new Label("Show all boats available"));
        TitledPane pane2 = new TitledPane("Cars"  , new Label("Show all cars available"));
        TitledPane pane3 = new TitledPane("Planes", new Label("Show all planes available"));

        accordion.getPanes().add(pane1);
        accordion.getPanes().add(pane2);
        accordion.getPanes().add(pane3);

        VBox vBox = new VBox(accordion);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}