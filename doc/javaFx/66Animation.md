JavaFX动画支持使您能够动画JavaFX形状和控件，例如移动或旋转它们。 JavaFX动画支持是由JavaFX Timeline类提供的。 Timeline类表示一个你可以“播放”的动画时间轴。 你可以将关键帧添加到时间轴上，然后动画功能在此之间进行插值。  

**示例**

```java
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A simple JavaFX animation examples. Animates a Circle's X property by
 * translating (moving) it 200 points over 10 seconds.
 */

public class AnimationExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        Circle circle = new Circle(50, 150, 50, Color.RED);

        // change circle.translateXProperty from it's current value to 200
        KeyValue keyValue = new KeyValue(circle.translateXProperty(), 200);

        // over the course of 5 seconds
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(10), keyValue);
        Timeline timeline = new Timeline(keyFrame);

        Scene scene = new Scene(new Pane(circle), 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

        timeline.play();
    }
}
```