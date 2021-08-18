JavaFX HBox组件是一个布局组件，它将所有子节点(组件)放置在水平行中。Java HBox组件由javafx.scene.layout.HBox类表示。

### 1. 创建一个HBox

```java
HBox hbox = new HBox();
```

**添加组件到Hbox**

```java
Button button1 = new Button("Button Number 1");
Button button2 = new Button("Button Number 2");

HBox hbox = new HBox(button1, button2);
```

### 2. 添加HBox到Scene

```java
package com.jenkov.javafx.layouts;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class HBoxExperiments extends Application  {

  @Override
  public void start(Stage primaryStage) throws Exception { 
    primaryStage.setTitle("HBox Experiment 1");

    Button button1 = new Button("Button Number 1");
    Button button2 = new Button("Button Number 2");

    HBox hbox = new HBox(button1, button2);

    Scene scene = new Scene(hbox, 200, 100);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
}
```

### 3.子节点属性

**间距**

```java
HBox hbox = new HBox(20, button1, button2);
```

```java
hbox.setSpacing(50);
```

**外边距**

```java
Button button1   = new Button("Button 1");
HBox hbox = new HBox(button1);
HBox.setMargin(button1, new Insets(10, 10, 10, 10));
```

**对齐**

```
hbox.setAlignment(Pos.BASELINE_CENTER);
```

```
hbox.setAlignment(Pos.BASELINE_CENTER);
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

**水平增长**

```java
Button button1   = new Button("Button 1");
HBox hbox = new HBox(button1);
HBox.setHgrow(button1, Priority.ALWAYS);
```

1. Policy.ALWAYS
2. Policy.SOMETIMES
3. Policy.NEVER

### 4. 填充高度

JavaFX HBox fillHeight属性可以用来告诉HBox控件是应该扩展子控件的高度来填充HBox的整个高度，还是保持子控件的优先高度。

fillHeight属性只影响子组件，这些子组件的高度实际上可以改变。例如，Button默认不改变其高度。它的最大高度被设置为它的首选高度。但是，你可以通过设置Button或任何其他你想嵌套在HBox中的组件的最大高度，使其不同于其首选值来覆盖它。

下面的例子展示了fillHeight属性的工作原理:

```java
Button button1   = new Button("Button 1");
button1.setMaxHeight(99999.0D); //or Double.MAX_VALUE;

HBox hbox = new HBox(button1);

hbox.setFillHeight(true);
```