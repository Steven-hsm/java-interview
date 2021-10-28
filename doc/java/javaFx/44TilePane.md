JavaFX TilePane是一种布局组件，它将其子组件布局在大小相等的网格中。JavaFX TilePane布局组件由JavaFX .scene.layout.TilePane类表示

### 1. 创建一个TilePane

```java
TilePane tilePane = new TilePane();
```

**添加子节点到TilePane上**

```java
primaryStage.setTitle("TilePane Experiment");

Button button1 = new Button("Button 1");
Button button2 = new Button("Button Number 2");
Button button3 = new Button("Button No 3");
Button button4 = new Button("Button No 4");
Button button5 = new Button("Button 5");
Button button6 = new Button("Button Number 6");

TilePane tilePane = new TilePane();

tilePane.getChildren().add(button1);
tilePane.getChildren().add(button2);
tilePane.getChildren().add(button3);
tilePane.getChildren().add(button4);
tilePane.getChildren().add(button5);
tilePane.getChildren().add(button6);

tilePane.setTileAlignment(Pos.TOP_LEFT);
```

### 2. 添加TilePane到Scene上

```java
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;


public class TilePaneExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TilePane Experiment");

        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button Number 2");
        Button button3 = new Button("Button No 3");
        Button button4 = new Button("Button No 4");
        Button button5 = new Button("Button 5");
        Button button6 = new Button("Button Number 6");

        TilePane tilePane = new TilePane();

        tilePane.getChildren().add(button1);
        tilePane.getChildren().add(button2);
        tilePane.getChildren().add(button3);
        tilePane.getChildren().add(button4);
        tilePane.getChildren().add(button5);
        tilePane.getChildren().add(button6);

        tilePane.setTileAlignment(Pos.TOP_LEFT);

        Scene scene = new Scene(tilePane, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```

### 3. 横向或者纵向间距

```java
tilePane.setHgap(10);
tilePane.setVgap(10);
```

