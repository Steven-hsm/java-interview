JavaFX DatePicker控件允许用户输入日期或从类似向导的弹出对话框中选择日期。弹出对话框只显示有效的日期，因此这是用户选择日期并确保日期选择器文本字段中输入的日期和日期格式有效的一种更简单的方法。JavaFX DatePicker由类JavaFX .scene.control.DatePicker表示。

DatePicker是ComboBox类的一个子类，因此与ComboBox类有一些相似之处。

### 1. 创建一个DatePicker

```java
DatePicker datePicker = new DatePicker();
```

### 2. 添加DatePicker到Scene上

```
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DatePickerExperiments extends Application  {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Button Experiment 1");

        DatePicker datePicker = new DatePicker();

        HBox hbox = new HBox(datePicker);

        Scene scene = new Scene(hbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```

### 3. 读取选中的值

```java
LocalDate value = datePicker.getValue();
```