JavaFX FlowPane是一个布局组件，它可以垂直或水平地布局其子组件，如果一行没有足够的空间，它可以将组件包装到下一行或下一列。JavaFX FlowPane布局组件由JavaFX .scene.layout.FlowPane类表示

### 1. 创建一个FlowPane

```java
FlowPane flowpane = new FlowPane();
```

**添加组件到FlowPane上**

```java
Button button1 = new Button("Button Number 1");
Button button2 = new Button("Button Number 2");
Button button3 = new Button("Button Number 3");

FlowPane flowpane = new FlowPane();

flowpane.getChildren().add(button1);
flowpane.getChildren().add(button2);
flowpane.getChildren().add(button3);
```

### 2. 添加FlowPane到Scene上

```java
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class FlowPaneExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("HBox Experiment 1");

        Button button1 = new Button("Button Number 1");
        Button button2 = new Button("Button Number 2");
        Button button3 = new Button("Button Number 3");

        FlowPane flowpane = new FlowPane();

        flowpane.getChildren().add(button1);
        flowpane.getChildren().add(button2);
        flowpane.getChildren().add(button3);

        Scene scene = new Scene(flowpane, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```

### 3. 垂直或者水平间距设置

```java
flowpane.setHgap(10);
flowpane.setVgap(10);
```

### 4. 方向

```java
flowpane.setOrientation(Orientation.VERTICAL);
```