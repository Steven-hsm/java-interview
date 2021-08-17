

JavaFX PasswordField控件允许JavaFX应用程序的用户输入密码，然后应用程序可以读取密码。PasswordField控件不显示输入的文本。相反，它会为输入的每个字符显示一个圆圈。JavaFX PasswordField控件由JavaFX .scene.control.PasswordField类表示。

### 1. 创建PasswordField

```java
PasswordField passwordField = new PasswordField();
```

### 2. 添加PasswordField到Scene

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PasswordFieldExperiments extends Application  {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PasswordField Experiment 1");

        PasswordField passwordField = new PasswordField();

        HBox hbox = new HBox(passwordField);

        Scene scene = new Scene(hbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```

### 3. 获取PasswordField的值

```java
passwordField.getText()
```

