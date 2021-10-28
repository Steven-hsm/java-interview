JavaFX ContextMenu组件为JavaFX控件提供了一个标准的右键菜单。JavaFX ContextMenu由JavaFX .scene.control.ContextMenu类表示。您创建一个ContextMenu实例，并将其附加到您希望ContextMenu处于活动状态的JavaFX控件上。

### 1.  创建ContextMenu

```java
ContextMenu contextMenu = new ContextMenu();
```

### 2. 添加菜单选项到ContextMenu

```java
ContextMenu contextMenu = new ContextMenu();

MenuItem menuItem1 = new MenuItem("Choice 1");
MenuItem menuItem2 = new MenuItem("Choice 2");
MenuItem menuItem3 = new MenuItem("Choice 3");

menuItem3.setOnAction((event) -> {
    System.out.println("Choice 3 clicked!");
});

contextMenu.getItems().addAll(menuItem1,menuItem2,menuItem3);
```

### 3. 添加ContextMenu

```java
TextArea textArea = new TextArea();

textArea.setContextMenu(contextMenu);
```

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContextMenuExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("Choice 1");
        MenuItem menuItem2 = new MenuItem("Choice 2");
        MenuItem menuItem3 = new MenuItem("Choice 3");

        menuItem3.setOnAction((event) -> {
            System.out.println("Choice 3 clicked!");
        });

        contextMenu.getItems().addAll(menuItem1,menuItem2,menuItem3);

        TextArea textArea = new TextArea();

        textArea.setContextMenu(contextMenu);

        VBox vBox = new VBox(textArea);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX App");

        primaryStage.show();
    }
}
```

