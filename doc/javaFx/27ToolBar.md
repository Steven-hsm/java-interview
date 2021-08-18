JavaFX工具栏类(JavaFX .scene.control.ToolBar)是一个包含按钮或图标的水平或垂直工具条，通常用于选择JavaFX应用程序中的不同工具。实际上，JavaFX工具栏可以包含其他JavaFX控件，而不仅仅是按钮和图标。实际上，您可以将任何JavaFX控件插入到ToolBar中。

### 1.创建TookBar

```java
ToolBar toolBar = new ToolBar();
```

**添加项目到ToolBar中**

```java
Button button = new Button("Click Me");
toolBar.getItems().add(button);
```

### 2. 添加ToolBar到Scene

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ToolBarExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        ToolBar toolBar = new ToolBar();

        Button button1 = new Button("Button 1");
        toolBar.getItems().add(button1);

        Button button2 = new Button("Button 2");
        toolBar.getItems().add(button2);

        VBox vBox = new VBox(toolBar);

        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
```

### 3. 垂直方向ToolBar

```java
toolBar.setOrientation(Orientation.VERTICAL);
```

### 4. 分割项目

```java
Button button1 = new Button("Button 1");
toolBar.getItems().add(button1);

toolBar.getItems().add(new Separator());

Button button2 = new Button("Button 2");
toolBar.getItems().add(button2);
```

