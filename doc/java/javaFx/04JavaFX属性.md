JavaFX属性是JavaFX控件的一种特殊成员变量。JavaFX属性通常用于包含控件属性，如X和Y位置、宽度和高度、文本、子控件和JavaFX控件的其他中心属性。您可以将更改监听器附加到JavaFX属性，这样当属性的值发生变化时，其他组件就可以得到通知，您还可以将属性绑定到其他属性，这样当一个属性值发生变化时，另一个属性值也会发生变化。在本JavaFX属性教程中，我将解释JavaFX属性是如何工作的，以及如何使用它们。

下面是一个JavaFX GUI示例，展示了如何访问JavaFX窗格的widthProperty和prefWidthProperty，以及向两者添加更改侦听器。请注意，一个更改侦听器是如何作为匿名Java类实现的，另一个是如何作为Java Lambda表达式实现的。这只是向您展示了实现将事件侦听器附加到JavaFX属性这一相同目标的两种不同方法。

```java
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PropertyExample extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage primaryStage) {

    Pane pane = new Pane();

    ReadOnlyDoubleProperty widthProperty = pane.widthProperty();
    widthProperty.addListener( new ChangeListener<Number> (){
      @Override
      public void changed(
        ObservableValue<? extends Number> observableValue,
        Number oldVal, Number newVal) {

          System.out.println("widthProperty changed from "
            + oldVal.doubleValue() + " to " + newVal.doubleValue());
      }
    });


    DoubleProperty prefWidthProperty = pane.prefWidthProperty();
    prefWidthProperty.addListener(
      (ObservableValue<? extends Number> prop,
        Number oldVal, Number newVal) -> {

        System.out.println("prefWidthProperty changed from "
          + oldVal.doubleValue() + " to " + newVal.doubleValue());
    });

    prefWidthProperty.set(123);

    Scene scene = new Scene(pane, 1024, 800, true);
    primaryStage.setScene(scene);
    primaryStage.setTitle("2D Example");

    primaryStage.show();
  }
}
```

当指令prefWidthProperty.set(123);时，将调用prefWidthProperty更改侦听器。此外，每当UI调整大小时，窗格也会调整大小，并且widthProperty更改侦听器将被调用。