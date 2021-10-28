JavaFX Canvas控件提供了一个图形画布，您可以使用方法调用形式的绘图命令来绘制图形。 JavaFX Canvas控件在设计和功能上与HTML5 Canvas非常相似，如果你熟悉的话。 JavaFX Canvas控件可以用于绘制性能更高的图形，而不是使用附加到场景图中的JavaFX 2D形状组合图形，所以如果你需要更高性能的图形，JavaFX Canvas控件是最好的选择。  

**示例**

```
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
    
public class CanvasExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Canvas canvas = new Canvas();
        canvas.setHeight(512);
        canvas.setWidth(512);

        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();

        graphicsContext2D.setFill(Color.valueOf("#ff0000"));
        graphicsContext2D.fillRect(100, 100, 200, 200);

        graphicsContext2D.setStroke(Color.valueOf("#0000ff"));
        graphicsContext2D.strokeRect(200, 200, 200, 200);

        VBox vbox = new VBox(canvas);
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
```