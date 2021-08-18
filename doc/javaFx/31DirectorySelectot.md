JavaFX DirectoryChooser是一个对话框，允许用户通过文件资源管理器从用户的本地计算机选择目录。JavaFX DirectoryChooser在JavaFX .stage.DirectoryChooser类中实现。在这个JavaFX DirectoryChooser教程中，我将向您展示如何使用DirectoryChooser对话框。

### 1. 创建一个DirectoryChooser

```java
DirectoryChooser directoryChooser = new DirectoryChooser();
```

### 2. 显示目录对话框

```java
File selectedDirectory = directoryChooser.showDialog(primaryStage);
```

### 3. 设置初始目录

```java
directoryChooser.setInitialDirectory(new File("data/json/invoices"));
```

### 4. 完整案例

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class DirectoryChooserExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));

        Button button = new Button("Select Directory");
        button.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(primaryStage);

            System.out.println(selectedDirectory.getAbsolutePath());
        });


        VBox vBox = new VBox(button);
        //HBox hBox = new HBox(button1, button2);
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
```
