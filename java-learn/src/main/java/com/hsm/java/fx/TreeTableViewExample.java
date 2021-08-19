package com.hsm.java.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

/**
 * @Classname TreeTableViewExample
 * @Description TODO
 * @Date 2021/8/19 20:43
 * @Created by huangsm
 */
public class TreeTableViewExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TreeTableView<Car> treeTableView = new TreeTableView<Car>();

        TreeTableColumn<Car, String> treeTableColumn1 = new TreeTableColumn<>("Brand");
        TreeTableColumn<Car, String> treeTableColumn2 = new TreeTableColumn<>("Model");

        treeTableColumn1.setCellValueFactory(new TreeItemPropertyValueFactory<>("brand"));
        treeTableColumn2.setCellValueFactory(new TreeItemPropertyValueFactory<>("model"));

        treeTableView.getColumns().add(treeTableColumn1);
        treeTableView.getColumns().add(treeTableColumn2);


        TreeItem mercedes1 = new TreeItem(new Car("Mercedes", "SL500"));
        TreeItem mercedes2 = new TreeItem(new Car("Mercedes", "SL500 AMG"));
        TreeItem mercedes3 = new TreeItem(new Car("Mercedes", "CLA 200"));

        TreeItem mercedes = new TreeItem(new Car("Mercedes", "..."));
        mercedes.getChildren().add(mercedes1);
        mercedes.getChildren().add(mercedes2);
        mercedes.getChildren().add(mercedes3);

        TreeItem audi1 = new TreeItem(new Car("Audi", "A1"));
        TreeItem audi2 = new TreeItem(new Car("Audi", "A5"));
        TreeItem audi3 = new TreeItem(new Car("Audi", "A7"));

        TreeItem audi = new TreeItem(new Car("Audi", "..."));
        audi.getChildren().add(audi1);
        audi.getChildren().add(audi2);
        audi.getChildren().add(audi3);

        TreeItem cars = new TreeItem(new Car("Cars", "..."));
        cars.getChildren().add(audi);
        cars.getChildren().add(mercedes);

        treeTableView.setRoot(cars);
        Scene scene = new Scene(treeTableView);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
