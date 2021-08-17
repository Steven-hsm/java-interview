package com.hsm.java.fx;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ListViewExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ListView Experiment 1");

        ListView listView = new ListView();



        listView.getItems().add(new User(1,"张三"));
        listView.getItems().add(new User(2,"李四"));
        listView.getItems().add(new User(3,"王五"));

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Button button = new Button("Read Selected Value");

        button.setOnAction(event -> {
            ObservableList selectedIndices = listView.getSelectionModel().getSelectedIndices();

            for(Object o : selectedIndices){
                System.out.println("o = " + o + " (" + o.getClass() + ")");
            }
        });


        VBox vBox = new VBox(listView, button);

        Scene scene = new Scene(vBox, 300, 120);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}