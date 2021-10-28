JavaFX GridPane是一个布局组件，它在网格中布局其子组件。网格中单元格的大小取决于GridPane中显示的组件，但有一些规则。同一行中的所有单元格将具有相同的高度，同列中的所有单元格将具有相同的宽度。不同的行可以有不同的高度，不同的列可以有不同的宽度。

JavaFX GridPane与TilePane的不同之处在于，GridPane允许不同大小的单元格，而TilePane使所有的tile都具有相同的大小。

GridPane中的行数和列数取决于添加到其中的组件。当您向GridPane添加组件时，您可以在单元格(行、列)中说明该组件应该插入什么单元格(行、列)，以及该组件应该跨越多少行和列。

JavaFX GridPane布局组件由JavaFX .scene.layout.GridPane类表示

### 1. 创建一个 GridPane

```java
GridPane gridPane = new GridPane();
```

**添加子节点到GridPane**

```java
Button button1 = new Button("Button 1");
Button button2 = new Button("Button 2");
Button button3 = new Button("Button 3");
Button button4 = new Button("Button 4");
Button button5 = new Button("Button 5");
Button button6 = new Button("Button 6");

GridPane gridPane = new GridPane();

gridPane.add(button1, 0, 0, 1, 1);
gridPane.add(button2, 1, 0, 1, 1);
gridPane.add(button3, 2, 0, 1, 1);
gridPane.add(button4, 0, 1, 1, 1);
gridPane.add(button5, 1, 1, 1, 1);
gridPane.add(button6, 2, 1, 1, 1);
```

### 2. 添加GridPane到Scene

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class GridPaneExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GridPane Experiment");

        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");
        Button button3 = new Button("Button 3");
        Button button4 = new Button("Button 4");
        Button button5 = new Button("Button 5");
        Button button6 = new Button("Button 6");

        GridPane gridPane = new GridPane();

        gridPane.add(button1, 0, 0, 1, 1);
        gridPane.add(button2, 1, 0, 1, 1);
        gridPane.add(button3, 2, 0, 1, 1);
        gridPane.add(button4, 0, 1, 1, 1);
        gridPane.add(button5, 1, 1, 1, 1);
        gridPane.add(button6, 2, 1, 1, 1);

        Scene scene = new Scene(gridPane, 240, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```

### 3. 跨越多行或者多列

```java
gridPane.add(button1, 0, 0, 2, 2);

gridPane.add(button2, 2, 0, 1, 1);
gridPane.add(button3, 2, 1, 1, 1);
gridPane.add(button4, 0, 2, 1, 1);
gridPane.add(button5, 1, 2, 1, 1);
gridPane.add(button6, 2, 2, 1, 1);
```

### 4. 垂直或者水平间距

```java
gridPane.setHgap(10);
gridPane.setVgap(10);
```