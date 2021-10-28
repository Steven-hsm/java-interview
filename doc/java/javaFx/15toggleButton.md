JavaFX ToggleButton是一个可以选择或不选择的按钮。就像一个按钮，当你按它的时候，它会留在里面，当你下次按它的时候，它又会出来。已切换-未切换。JavaFX ToggleButton由JavaFX .scene.control.ToggleButton类表示。

### 1. 创建一个ToggleButton

```java
ToggleButton toggleButton1 = new ToggleButton("Left");
```

### 2. 将ToggleButton显示在Scene上

```
package com.jenkov.javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ToggleButtonExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("HBox Experiment 1");


        ToggleButton toggleButton1 = new ToggleButton("Left");

        HBox hbox = new HBox(toggleButton1);


        Scene scene = new Scene(hbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```

### 3. ToggleButton属性

**设置文本**

```java
ToggleButton toggleButton = new ToggleButton("Toggle This!");
toggleButton.setText("New Text");
```

**字体**

```java
ToggleButton toggleButton = new ToggleButton("Toggle This!");

Font arialFontBold36  = Font.font("Arial", FontWeight.BOLD, 36);

toggleButton.setFont(arialFontBold36);
```

### 4. 读取选择的状态

```java
boolean isSelected = toggleButton1.isSelected();
```

### 5. 切换组

您可以将JavaFX ToggleButton实例分组到ToggleGroup中。一个ToggleGroup在任何时候最多允许一个ToggleButton被切换(按下)。因此，ToggleGroup中的ToggleButton实例的功能类似于单选按钮。

下面是一个JavaFX ToggleGroup的例子:

```java
package com.jenkov.javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ToggleButtonExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("HBox Experiment 1");


        ToggleButton toggleButton1 = new ToggleButton("Left");
        ToggleButton toggleButton2 = new ToggleButton("Right");
        ToggleButton toggleButton3 = new ToggleButton("Up");
        ToggleButton toggleButton4 = new ToggleButton("Down");

        ToggleGroup toggleGroup = new ToggleGroup();

        toggleButton1.setToggleGroup(toggleGroup);
        toggleButton2.setToggleGroup(toggleGroup);
        toggleButton3.setToggleGroup(toggleGroup);
        toggleButton4.setToggleGroup(toggleGroup);

        HBox hbox = new HBox(toggleButton1, toggleButton2, toggleButton3, toggleButton4);


        Scene scene = new Scene(hbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```

**读取切换组的选择状态**

```java
ToggleButton selectedToggleButton =
        (ToggleButton) toggleGroup.getSelectedToggle();
```

如果没有选择ToggleButton, getSelectedToggle()方法将返回null。