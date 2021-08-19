JavaFX拖放支持使您能够设计您的JavaFX应用程序，以便用户可以将JavaFX节点(控件)拖放到其他JavaFX节点上，并使您的应用程序能够侦听和响应这些拖放事件。 在有意义的情况下，拖放可以让用户更容易地指定操作，例如将文件拖到新的位置，或将数据拖到分析操作等。  

顺便说一下，JavaFX的拖放支持与HTML5的拖放支持非常相似。 如果你已经知道HTML5的拖放功能(通过JavaScript)，那么就更容易理解JavaFX的拖放功能是如何工作的。 如果没有-直接从JavaFX拖放支持开始。  

### 1. 拖放源、拖放目表

JavaFX拖放支持将涉及拖放的JavaFX控件划分为拖放源和拖放目标类别。  

拖拽源是一个JavaFX控件(例如2D形状、ImageView等)，可以拖拽到另一个节点上。 通常，大多数JavaFX控件都可以用作拖动源。  

拖放目标是一个avaFX控件，您可以在其上拖放拖放源。 当将拖放源拖放到拖放目标上时，将触发一个事件，应用程序可以侦听该事件并对其作出响应。  

### 2. 示例

```java
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class DragAndDropExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        Circle circle = createCircle("#ff00ff", "#ff88ff",100);

        circle.setOnDragDetected((MouseEvent event) -> {
            System.out.println("Circle 1 drag detected");

            Dragboard db = circle.startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString("Circle source text");
            db.setContent(content);
        });
        circle.setOnMouseDragged((MouseEvent event) -> {
            event.setDragDetect(true);
        });

        Circle circle2 = createCircle("#00ffff", "#88ffff",300);

        circle2.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != circle2 && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        circle2.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            if (db.hasString()) {
                System.out.println("Dropped: " + db.getString());
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });

        Pane pane = new Pane();
        pane.getChildren().add(circle);
        pane.getChildren().add(circle2);

        Scene scene = new Scene(pane, 1024, 800, true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("2D Example");

        primaryStage.show();
    }

    private Circle createCircle(String strokeColor, String fillColor, double x) {
        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(200);
        circle.setRadius(50);
        circle.setStroke(Color.valueOf(strokeColor));
        circle.setStrokeWidth(5);
        circle.setFill(Color.valueOf(fillColor));
        return circle;
    }
}
```