JavaFX Accordion控件是一个容器控件，它可以在内部包含几个部分，每个部分都可以展开或折叠其内容。Accordion控件是由JavaFX类JavaFX .scene.control.Accordion实现的。其中显示的部分由JavaFX TitledPane控件组成。

### 1. 创建一个Accordion

```java
Accordion accordion = new Accordion();
```

**添加TitledPane到Accordion中**

```java
Accordion accordion = new Accordion();

TitledPane pane1 = new TitledPane("Boats" , new Label("Show all boats available"));
TitledPane pane2 = new TitledPane("Cars"  , new Label("Show all cars available"));
TitledPane pane3 = new TitledPane("Planes", new Label("Show all planes available"));

accordion.getPanes().add(pane1);
accordion.getPanes().add(pane2);
accordion.getPanes().add(pane3);
```

### 2.添加Accordion到Scene中

```java
Accordion accordion = new Accordion();
VBox vBox = new VBox(accordion);
Scene scene = new Scene(vBox);
primaryStage.setScene(scene);
primaryStage.show();
```

### 3. 完整例子

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccordionExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        Accordion accordion = new Accordion();

        TitledPane pane1 = new TitledPane("Boats" , new Label("Show all boats available"));
        TitledPane pane2 = new TitledPane("Cars"  , new Label("Show all cars available"));
        TitledPane pane3 = new TitledPane("Planes", new Label("Show all planes available"));

        accordion.getPanes().add(pane1);
        accordion.getPanes().add(pane2);
        accordion.getPanes().add(pane3);

        VBox vBox = new VBox(accordion);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
```