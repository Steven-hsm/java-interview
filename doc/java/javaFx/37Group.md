JavaFX Group组件是一个容器组件，它不对其子组件应用特殊的布局。所有子组件(节点)都位于0,0。JavaFX Group组件通常用于将某些效果或转换作为一个整体(作为一个组)应用到一组控件。如果需要对Group中的子组件进行一些布局，则将它们嵌套在布局组件中，并将布局组件添加到Group中。JavaFX Group组件由类JavaFX .scene.Group表示。

### 1. 创建一个组

```java
Group group = new Group();
```

**添加组件到组**

```java
Button button1 = new Button("Button Number 1");
Button button2 = new Button("Button 2");

Group group = new Group();

group.getChildren().add(button1);
group.getChildren().add(button2);
```

### 2. 添加Group到Scene

```java
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class GroupExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("HBox Experiment 1");

        Button button1 = new Button("Button Number 1");
        Button button2 = new Button("Button 2");

        Group group = new Group();

        group.getChildren().add(button1);
        group.getChildren().add(button2);

        Scene scene = new Scene(group, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```