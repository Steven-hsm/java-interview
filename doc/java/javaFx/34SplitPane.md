JavaFX SplitPane是一个容器控件，可以在其中包含多个其他组件。换句话说，SplitPane在它所包含的控件之间被分割。在SplitPane的控件之间是一个分隔符。用户可以移动分隔符来设置分配给每个控件的空间。

### 1. 创建一个SplitPane

```java
SplitPane splitPane = new SplitPane();
```

**添加Control到splitPane**

```java
SplitPane splitPane = new SplitPane();

VBox leftControl  = new VBox(new Label("Left Control"));
VBox rightControl = new VBox(new Label("Right Control"));

splitPane.getItems().addAll(leftControl, rightControl);
```

### 2. 添加更多的Control到splitPane

```java
SplitPane splitPane = new SplitPane();

VBox leftControl  = new VBox(new Label("Left Control"));
VBox midControl   = new VBox(new Label("Mid Control"));
VBox rightControl = new VBox(new Label("Right Control"));

splitPane.getItems().addAll(leftControl, midControl, rightControl);

Scene scene = new Scene(splitPane);

primaryStage.setScene(scene);
primaryStage.setTitle("JavaFX App");

primaryStage.show();
```

### 3. 完整例子

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SplitPaneExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        SplitPane splitPane = new SplitPane();

        VBox leftControl  = new VBox(new Label("Left Control"));
        VBox rightControl = new VBox(new Label("Right Control"));

        splitPane.getItems().addAll(leftControl, rightControl);

        Scene scene = new Scene(splitPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX App");

        primaryStage.show();
    }
}
```