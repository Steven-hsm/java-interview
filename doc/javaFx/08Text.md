JavaFX Text控件可以在JavaFX GUI中显示文本。JavaFX Text控件由JavaFX类JavaFX .scene. Text .Text表示。您可以设置Text控件使用的字体、文本大小、字体装饰和许多其他东西。

由于JavaFX Text控件是JavaFX Shape类的子类，Text类具有与其他JavaFX Shape对象相同的所有可用方法——例如填充和描边颜色和样式。

JavaFX Text控件也是JavaFX Node类的子类，所以Text类也有与任何其他JavaFX Node相同的方法，这意味着你可以对它设置效果等等。

### 1. 例子

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TextExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        Text text = new Text("This is a JavaFX text.");

        Scene scene = new Scene(new VBox(text), 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
```

### 2. 属性介绍

#### 2.1 设置文本

```java
Text text = new Text();

text.setText("This is the text to display");
```

#### 2.2 设置字体

```java
Text text = new Text("Some Text");

text.setFont(Font.font("Arial"));
```

#### 2.3 设置填充色

```java
text.setFill(Color.YELLOW);
```

#### 2.4 设置字体色

```java
text.setStroke(Color.GREEN);
```

#### 2.5 设置文本位置

```java
text.setX(50);
text.setY(25);
```

#### 2.6 文本位置

```
text.setTextOrigin(VPos.BASELINE);
```

- VPos.BASELINE
- VPos.BOTTOM
- VPos.CENTER
- VPos.TOP

#### 2.7 多行文本

JavaFX Text控件将根据以下规则将显示的文本拆分为多行:

* 如果文本包含一个换行符(\n)。

  ```java
  Text text = new Text("This is a JavaFX text.\nLine 2");
  ```

* 如果文本宽度超过“文本”控件上设置的换行宽度。

  ```
  Text text = new Text("This is a longer JavaFX text.");
  text.setWrappingWidth(80);
  ```

#### 2.8 删除线

```
text.setStrikethrough(true);
```

#### 2.9 下划线

```
text.setUnderline(true);
```

#### 2.10 字体平滑技术

```
text.setFontSmoothingType(FontSmoothingType.GRAY);

text.setFontSmoothingType(FontSmoothingType.LCD);
```