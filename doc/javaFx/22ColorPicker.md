JavaFX ColorPicker控件允许用户在弹出对话框中选择颜色。选择的颜色可以稍后由JavaFX应用程序从ColorPicker读取。JavaFX ColorPicker控件由类JavaFX .scene.control.ColorPicker表示。

### 1. 完整的例子

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ColorPickerExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        ColorPicker colorPicker = new ColorPicker();

        Color value = colorPicker.getValue();

        VBox vBox = new VBox(colorPicker);
        //HBox hBox = new HBox(button1, button2);
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
```

**创建ColorPicker**

```java
ColorPicker colorPicker = new ColorPicker();
```

**获取选中的颜色**

```java
Color value = colorPicker.getValue();
```

