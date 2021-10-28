JavaFX TextField控件允许JavaFX应用程序的用户输入可被应用程序读取的文本。JavaFX TextField控件由JavaFX .scene.control.TextField类表示。

### 1. 创建一个TextField

```java
TextField textField = new TextField();
```

### 2. 添加TextField到Scene

```
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class TextFieldExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("HBox Experiment 1");

        TextField textField = new TextField();

        HBox hbox = new HBox(textField);

        Scene scene = new Scene(hbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```

### 3. 获取TextField内容

```java
textField.getText()
```

### 4. 设置TextField内容

```java
textField.setText("Initial value");
```