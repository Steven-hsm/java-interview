JavaFX HTMLEditor是一个高级HTML编辑器，它使用户编辑HTML比用文本编写完整的HTML标记更容易。HTMLEditor包含一组按钮，可用于设置已编辑文本的所见即所得样式。JavaFX HTMLEditor由JavaFX .scene.web.HTMLEditor类表示。

### 1. 创建HTMLEditor

```java
HTMLEditor htmlEditor = new HTMLEditor();
```

### 2. 获取html

```java
String htmlText = htmlEditor.getHtmlText();
```

### 3. 设置html

```java
String htmlText = "<b>Bold text</b>";

htmlEditor.setHtmlText(htmlText);
```

### 4. 完整例子

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class HtmlEditorExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        HTMLEditor htmlEditor = new HTMLEditor();

        VBox vBox = new VBox(htmlEditor);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX App");

        primaryStage.show();
    }
}
```
