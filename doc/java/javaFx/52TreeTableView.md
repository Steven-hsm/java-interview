JavaFX TreeTableView类是JavaFX TreeView和JavaFX TableView的组合。总的来说，JavaFX TreeTableView是一个TableView，其中最左边的列包含一棵项目树。其余的列是普通的表列。

JavaFX TreeTableView以行显示其树中的每个项目。换句话说，显示在每个树节点右侧的列属于左边树中的项目。JavaFX TreeTableView最左边列中的树项可以展开和折叠。在展开和折叠树项时，任何显示或隐藏树项的行也会显示在右侧的列中。

### 1.创建一个TreeTableView

```java
TreeTableView<Car> treeTableView = new TreeTableView<Car>();
```

### 2. 完整的例子

```java
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
```

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private String brand = null;
    private String model = null;
}
```

