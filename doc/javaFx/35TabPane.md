JavaFX TabPane是一个容器控件，它可以在内部包含多个选项卡(部分)，可以通过单击TabPane顶部的标题选项卡来显示。一次只能显示一个选项卡。这就像一个文件夹是打开的。JavaFX TabPane控件由JavaFX .scene.control.TabPane类实现。

### 1. 创建一个TabPane

```java
TabPane tabPane = new TabPane();
```

**添加Tab到TabPane**

```java
TabPane tabPane = new TabPane();

Tab tab1 = new Tab("Planes", new Label("Show all planes available"));
Tab tab2 = new Tab("Cars"  , new Label("Show all cars available"));
Tab tab3 = new Tab("Boats" , new Label("Show all boats available"));

tabPane.getTabs().add(tab1);
tabPane.getTabs().add(tab2);
tabPane.getTabs().add(tab3);
```

### 2. 获取选中TabPane

```java
Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
```

### 3. 完整案例

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TabPaneExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Planes", new Label("Show all planes available"));
        Tab tab2 = new Tab("Cars"  , new Label("Show all cars available"));
        Tab tab3 = new Tab("Boats" , new Label("Show all boats available"));

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);

        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX App");

        primaryStage.show();
    }
}
```