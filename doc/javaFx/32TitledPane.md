JavaFX TitledPane控件是一个容器控件，它将其内容显示在一个窗格(框)中，该窗格(框)的顶部包含一个标题——因此得名TitledPane。TitledPane控件是由javafx.scene.control.TitledPane类实现的。在这个JavaFX TitledPane教程中，我们将看看如何使用TitledPane控件。

### 1.创建一个TitledPane

```java
Label label = new Label("The content inside the TitledPane");
TitledPane titledPane = new TitledPane("The Title", label);
```

### 2. t添加TitledPane到Scene上

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TitledPaneExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("The content inside the TitledPane");
        TitledPane titledPane = new TitledPane("The Title", label);

        Scene scene = new Scene(new VBox(titledPane));
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
```

### 3. 收缩展开TitledPane

```java
titledPane.setExpanded(true);
titledPane.setExpanded(false);
```

### 4. 禁用收缩

```java
Label label = new Label("The content inside the TitledPane");
TitledPane titledPane = new TitledPane("The Title", label);

titledPane.setCollapsible(false);
```