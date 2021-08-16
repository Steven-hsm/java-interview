JavaFX超链接控件是一个文本，它充当一个按钮，这意味着您可以配置一个超链接，以便在用户单击它时执行某些操作。就像网页上的超链接。JavaFX超链接控件由JavaFX .scene.control.Hyperlink类表示。

### 1. 例子

```
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HyperlinkExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        Hyperlink link = new Hyperlink("Click Me!");

        VBox vBox = new VBox(link);
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
```

### 2. 创建一个HyperLink

```java
Hyperlink link = new Hyperlink("Click me!");
```

### 3. 设置HyperLink文本

```java
Hyperlink link = new Hyperlink("Click me!");
link.setText("New link text");
```

### 4. 设置HyperLink字体

```java
Hyperlink link = new Hyperlink("Click Me!");

Font courierNewFontBold36 = Font.font("Courier New", FontWeight.BOLD, 36);

link.setFont(courierNewFontBold36);
```

### 5. 设置HyperLink动作

```
Hyperlink link = new Hyperlink("Click me!");

link.setOnAction(e -> {
    System.out.println("The Hyperlink was clicked!");
});
```