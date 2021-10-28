JavaFX FileChooser类(JavaFX .stage.FileChooser)是一个对话框，允许用户通过文件资源管理器从用户的本地计算机选择一个或多个文件。JavaFX FileChooser是在JavaFX .stage.FileChooser类中实现的。在这个JavaFX FileChooser教程中，我将向您展示如何使用JavaFX FileChooser对话框。

### 1. 创建一个FileChooser

```java
FileChooser fileChooser = new FileChooser();
```

### 2. 显示文件选择器对话框

```java
File selectedFile = fileChooser.showOpenDialog(stage);
```

### 3. 设置初始目录

```java
fileChooser.setInitialDirectory(new File("data"));
```

### 4. 设置初始文件名

```java
fileChooser.setInitialFileName("myfile.txt");
```

### 5. 添加文件名过滤器

```java
FileChooser fileChooser = new FileChooser();

fileChooser.getExtensionFilters().addAll(
     new FileChooser.ExtensionFilter("Text Files", "*.txt")
    ,new FileChooser.ExtensionFilter("HTML Files", "*.htm")
);
```

### 6.完整案例

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileChooserExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        FileChooser fileChooser = new FileChooser();

        Button button = new Button("Select File");
        button.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
        });

        VBox vBox = new VBox(button);
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
```