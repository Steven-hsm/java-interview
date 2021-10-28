JavaFX Pane类是一个布局容器，它可以在内部包含其他JavaFX组件，并将它们进行布局。实际上，JavaFX Pane类实际上没有提供任何布局算法。Pane类只是在组件本身想要定位的位置上显示它所包含的组件。换句话说，Pane类使用它的子组件指定的layoutX和layoutY来决定在哪里显示它们。

JavaFX窗格类JavaFX .scene.layout。窗格是JavaFX Region类的一个子类，因此它继承了Region类的所有功能。这包括边框、填充、背景设置等功能。

### 1.创建一个Pane

```java
Pane pane = new Pane();
```

**添加组件到Pane**

```java
Pane  pane  = new Pane();

pane.getChildren().add(new Label("Hello Pane"));
```

### 2. 添加Pane到Scene

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PaneExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        Pane  pane  = new Pane();

        pane.getChildren().add(new Label("Hello Pane"));

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
```