JavaFX RadioButton是一个可以选择或不选择的按钮。RadioButton与JavaFX ToggleButton非常相似，但不同的是，一旦选中了RadioButton，就不能“取消选中”。如果RadioButton是ToggleGroup的一部分，那么一旦第一次选择了RadioButton，就必须在ToggleGroup中选择一个RadioButton。

JavaFX RadioButton由类JavaFX .scene.control.RadioButton表示。RadioButton类是ToggleButton类的一个子类。

### 1. 创建一个RadioButton

```java
RadioButton radioButton1 = new RadioButton("Left");
```

### 2. 添加RadioButton到Scene上

```java
package com.jenkov.javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class RadioButtonExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("HBox Experiment 1");

        RadioButton radioButton1 = new RadioButton("Left");

        HBox hbox = new HBox(radioButton1);

        Scene scene = new Scene(hbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
```

### 3. 读取选中的状态

```java
boolean isSelected = radioButton1.isSelected();
```

### 4. ToggleGroup

您可以将JavaFX RadioButton实例分组到ToggleGroup中。在任何时候，ToggleGroup最多允许选择一个单选按钮。

下面是一个JavaFX ToggleGroup的例子:

```java
package com.jenkov.javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ToggleGroupExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("HBox Experiment 1");

        RadioButton radioButton1 = new RadioButton("Left");
        RadioButton radioButton2 = new RadioButton("Right");
        RadioButton radioButton3 = new RadioButton("Up");
        RadioButton radioButton4 = new RadioButton("Down");

        ToggleGroup radioGroup = new ToggleGroup();

        radioButton1.setToggleGroup(radioGroup);
        radioButton2.setToggleGroup(radioGroup);
        radioButton3.setToggleGroup(radioGroup);
        radioButton4.setToggleGroup(radioGroup);

        HBox hbox = new HBox(radioButton1, radioButton2, radioButton3, radioButton4);

        Scene scene = new Scene(hbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
```

**读取组中被选中的元素**

```java
RadioButton selectedRadioButton =
        (RadioButton) toggleGroup.getSelectedToggle();
```

如果没有选中RadioButton, getSelectedToggle()方法将返回null。