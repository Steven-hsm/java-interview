JavaFX ProgressBar是一个控件，可以显示某些任务的进度。进度被设置为介于0和1之间的双精度值，其中0表示没有进度，1表示全部进度(任务完成)。JavaFX ProgressBar控件由JavaFX .scene.control.ProgressBar类表示。

### 1. 创建一个ProgressBar

```java
ProgressBar progressBar = new ProgressBar();
```

**初始化值**

```java
ProgressBar progressBar = new ProgressBar(0);
```

**设置进度条的值**

```java
progressBar.setProgress(0.5);
```

### 2. 完整的例子

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProgressBarExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        ProgressBar progressBar = new ProgressBar(0);

        progressBar.setProgress(0.5);

        VBox vBox = new VBox(progressBar);
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
```