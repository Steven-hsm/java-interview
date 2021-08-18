JavaFX TextArea控件允许JavaFX应用程序的用户输入多行文本，然后应用程序可以读取这些文本。JavaFX TextArea控件由JavaFX .scene.control.TextArea类表示。

### 1. 创建一个TextArea

```java
TextArea textArea = new TextArea();
```

### 2. 添加TextArea到Scene

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class TextAreaExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TextArea Experiment 1");

        TextArea textArea = new TextArea();

        VBox vbox = new VBox(textArea);

        Scene scene = new Scene(vbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```

### 3. 读取TextArea的值

```java
String text = textArea.getText();
```

### 4. 设置TextArea的值

```java
textArea.setText("New Text");
```

