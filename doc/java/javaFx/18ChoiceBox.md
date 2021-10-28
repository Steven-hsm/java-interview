JavaFX ChoiceBox控件使用户能够从预定义的选项列表中选择一个选项。JavaFX ChoiceBox控件由JavaFX .scene.control.ChoiceBox类表示。本JavaFX ChoiceBox教程将解释如何使用ChoiceBox类。

### 1. 创建一个ChoiceBox

```java
ChoiceBox choiceBox = new ChoiceBox();
```

**给项目添加选项**

```java
choiceBox.getItems().add("Choice 1");
choiceBox.getItems().add("Choice 2");
choiceBox.getItems().add("Choice 3");
```

### 2. 添加ChoiceBox到Scene上

```
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ChoiceBoxExperiments extends Application  {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ChoiceBox Experiment 1");

        ChoiceBox choiceBox = new ChoiceBox();

        choiceBox.getItems().add("Choice 1");
        choiceBox.getItems().add("Choice 2");
        choiceBox.getItems().add("Choice 3");

        HBox hbox = new HBox(choiceBox);

        Scene scene = new Scene(hbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```

### 3. 获取选中的值

```java
String value = (String) choiceBox.getValue();
```

### 4. 监听选择

```java
ChoiceBox choiceBox = new ChoiceBox();

choiceBox.getItems().add("Choice 1");
choiceBox.getItems().add("Choice 2");
choiceBox.getItems().add("Choice 3");

choiceBox.setOnAction((event) -> {
    int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex();
    Object selectedItem = choiceBox.getSelectionModel().getSelectedItem();

    System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
    System.out.println("   ChoiceBox.getValue(): " + choiceBox.getValue());
});
```