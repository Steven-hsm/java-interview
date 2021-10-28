JavaFX ComboBox控件允许用户从预定义的选项列表中选择一个选项，或者如果预定义的选项与用户想要选择的不匹配，则输入另一个值。JavaFX ComboBox控件由JavaFX . scene.control.com bobox类表示。本JavaFX ComboBox教程将解释如何使用ComboBox类。

### 1. 创建一个ComBox

```java
ComboBox comboBox = new ComboBox();
```

**添加选择器到Combox上**

```java
comboBox.getItems().add("Choice 1");
comboBox.getItems().add("Choice 2");
comboBox.getItems().add("Choice 3");
```

### 2. 添加Combox到Scene上

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ComboBoxExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ComboBox Experiment 1");

        ComboBox comboBox = new ComboBox();

        comboBox.getItems().add("Choice 1");
        comboBox.getItems().add("Choice 2");
        comboBox.getItems().add("Choice 3");


        HBox hbox = new HBox(comboBox);

        Scene scene = new Scene(hbox, 200, 120);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```

### 3. 使ComBox可编辑

```java
comboBox.setEditable(true);
```

### 4. 读取选中的值

```java
String value = (String) comboBox.getValue();
```

### 5. 监听

```java
ComboBox comboBox = new ComboBox();

comboBox.getItems().add("Choice 1");
comboBox.getItems().add("Choice 2");
comboBox.getItems().add("Choice 3");

comboBox.setOnAction((event) -> {
    int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();
    Object selectedItem = comboBox.getSelectionModel().getSelectedItem();

    System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
    System.out.println("   ComboBox.getValue(): " + comboBox.getValue());
});
```