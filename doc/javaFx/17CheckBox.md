JavaFX CheckBox是一个按钮，它可以处于三种不同的状态:选中、未选中和未知(不确定)。JavaFX CheckBox控件由JavaFX .scene.control.CheckBox类表示。

### 1. 创建一个CheckBox

```java
CheckBox checkBox1 = new CheckBox("Green");
```

### 2. 添加CheckBox到Scene上

```java
package com.jenkov.javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class CheckBoxExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("CheckBox Experiment 1");

        CheckBox checkBox1 = new CheckBox("Green");

        HBox hbox = new HBox(checkBox1);

        Scene scene = new Scene(hbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
```

### 3. 读取是否选中的状态

```java
boolean isSelected = checkBox1.isSelected();
```

### 4. 允许不确定状态

如前所述，JavaFX CheckBox可以处于不确定状态，这意味着既没有被选中，也没有被选中。用户只是还没有与CheckBox进行交互。

默认情况下，复选框不允许处于不确定状态。您可以使用setAllowIndeterminate()方法设置CheckBox是否允许处于不确定状态。下面是一个允许CheckBox处于不确定状态的例子:

```java
checkBox1.setAllowIndeterminate(true);
```

**读取不确定状态**

```java
boolean isIndeterminate = checkBox1.isIndeterminate();
```

