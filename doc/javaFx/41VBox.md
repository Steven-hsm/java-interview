JavaFX VBox组件是一个布局组件，它将它的所有子节点(组件)放置在一个垂直列中——每个子节点(组件)位于彼此之上。JavaFX VBox组件由JavaFX .scene.layout.VBox类表示。在本JavaFX VBox教程中，我将深入了解VBox组件用于控件布局的各种选项。

### 1. 创建VBox

```java
VBox vbox = new VBox();
```

**添加按钮到VBox上**

```java
Button button1 = new Button("Button Number 1");
Button button2 = new Button("Button Number 2");

VBox vbox = new VBox(button1, button2);
```

### 2. 添加VBox到Scene

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class VBoxExperiments extends Application{

  @Override
  public void start(Stage primaryStage) throws Exception { 
    primaryStage.setTitle("VBox Experiment 1");

    Button button1 = new Button("Button Number 1");
    Button button2 = new Button("Button Number 2");

    VBox vbox = new VBox(button1, button2);

    Scene scene = new Scene(vbox, 200, 100);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
}
```

### 3. 子节点属性

**间距**

```java
VBox vbox = new VBox(20, button1, button2);
```

```java
vbox.setSpacing(50);
```

**对齐方式**

```java
vbox.setAlignment(Pos.BASELINE_CENTER);
```

| Parameter           | Vertically | Horizontally |
| :------------------ | :--------- | :----------- |
| Pos.BASELINE_LEFT   | Baseline   | Left         |
| Pos.BASELINE_CENTER | Baseline   | Center       |
| Pos.BASELINE_RIGHT  | Baseline   | Right        |
| Pos.BOTTOM_LEFT     | Bottom     | Left         |
| Pos.BOTTOM_CENTER   | Bottom     | Center       |
| Pos.BOTTOM_RIGHT    | Bottom     | Right        |
| Pos.CENTER_LEFT     | Center     | Left         |
| Pos.CENTER          | Center     | Center       |
| Pos.CENTER_RIGHT    | Center     | Right        |
| Pos.TOP_LEFT        | Top        | Left         |
| Pos.TOP_CENTER      | Top        | Center       |
| Pos.TOP_RIGHT       | Top        | Right        |

**外边距**

```java
Button button = new Button("Button 1");

VBox vbox = new VBox(button);

VBox.setMargin(button, new Insets(10, 10, 10, 10));
```

**垂直增长**

```java
Button button = new Button("Button 1");

VBox vbox = new VBox(button);

VBox.setVgrow(button, Priority.ALWAYS);
```

1. Policy.ALWAYS
2. Policy.SOMETIMES
3. Policy.NEVER

### 4. 宽度填充

```java
Button button = new Button("Button 1");
button.setMaxWidth(99999D); //or Double.MAX_VALUE;

VBox vbox = new VBox(button);

vbox.setFillWidth(true);
```

### 5. CSS样式

```java
vbox.setStyle("-fx-padding: 16;");
```

| CSS Property      | Description                                                  |
| :---------------- | :----------------------------------------------------------- |
| -fx-padding       | Sets the padding between the edge of the VBox and the edge of the outermost child nodes. |
| -fx-border-style  | Sets the border style of the VBox, in case you want a visible border around it. |
| -fx-border-width  | Sets the border width.                                       |
| -fx-border-insets | Sets the border insets.                                      |
| -fx-border-radius | Sets the border radius (of corners).                         |
| -fx-border-color  | Sets the border color.                                       |

### 6. FXML

```java
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.VBox?>
<GridPane xmlns:fx="http://javafx.com/fxml"
          fx:controller="sample.Controller"
          alignment="center" hgap="10" vgap="10">

    <VBox>
        <Label>Text inside VBox</Label>
    </VBox>

</GridPane>
```

