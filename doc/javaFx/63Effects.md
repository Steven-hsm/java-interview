JavaFX效果是可以应用到JavaFX应用程序中的控件上的图形效果，以使应用程序GUI看起来更有趣。 JavaFX自带以下内置效果:  

- Drop Shadow
- Inner Shadow
- Shadow
- Reflection
- BoxBlur
- GaussianBlur
- MotionBlur
- Bloom
- Glow
- SepiaTone
- DisplacementMap
- ColorInput
- ImageInput
- Blend
- Lighting
- PerspectiveTransform

### 1. 示例

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class EffectsExample extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage primaryStage) {

    Circle circle = new Circle(50, 150, 50, Color.RED);

    circle.setEffect(
        new DropShadow(1, 20, 30, Color.web("#333333")));

    Scene scene = new Scene(new Pane(circle), 300, 250);
    primaryStage.setScene(scene);
    primaryStage.show();

  }
}
```

### 2. 在节点上运用图形效果

```java
circle.setEffect(
    new DropShadow(1, 20, 30, Color.web("#333333")));
```

**投影**

最有用的JavaFX效果之一是投影效果。 投影效果是由JavaFX类JavaFX .scene.effect. dropshadow提供的。 DropShadow类接受以下参数来指定投影的外观:  

- Radius
- X Offset
- Y Offset
- Color

所有这些参数都是可选的。 然而，在大多数情况下，你会想要设置他们的值，使下拉阴影给你想要的效果。 你可以在实例化DropShadow对象时通过构造函数设置这些参数，也可以通过指定的方法来设置DropShadow对象。  

半径参数指定阴影的边缘如何“展开”。 X偏移量和Y偏移量参数指定了投影效果应用到JavaFX节点的距离，阴影将被绘制。 颜色参数指定投影的颜色。  

下面是三个JavaFX Circle对象的例子，它们应用了不同的投影效果，演示了在DropShadow上设置的不同参数。 在代码示例之后，您将看到一个屏幕截图，它直观地显示了效果。  

```jaav
public void start(Stage primaryStage) {


        DropShadow dropShadow1 = new DropShadow();
        dropShadow1.setRadius(1);
        dropShadow1.setOffsetX(10);
        dropShadow1.setOffsetY(10);
        dropShadow1.setColor(Color.web("#333333"));

        Circle circle1 = new Circle(75, 75, 50, Color.RED);
        circle1.setEffect(dropShadow1);

        DropShadow dropShadow2 = new DropShadow();
        dropShadow2.setRadius(5);
        dropShadow2.setOffsetX(20);
        dropShadow2.setOffsetY(20);
        dropShadow2.setColor(Color.web("#660066"));

        Circle circle2 = new Circle(200, 75, 50, Color.GREEN);
        circle2.setEffect(dropShadow2);

        DropShadow dropShadow3= new DropShadow();
        dropShadow3.setRadius(25);
        dropShadow3.setOffsetX(30);
        dropShadow3.setOffsetY(30);
        dropShadow3.setColor(Color.web("#006666"));

        Circle circle3 = new Circle(325, 75, 50, Color.BLUE);
        circle3.setEffect(dropShadow3);

        Scene scene = new Scene(new Pane(circle1, circle2, circle3), 425, 175);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
```

**倒影**

JavaFX反射效果向JavaFX节点添加了一个类似镜像的反射。 反射效果是由javafx.scene.effect.Reflection类提供的。 Reflection类接受以下影响反射结果的参数:  

- topOffset
- topOpacity
- bottomOpacity
- fraction

topOffset参数指定了反射效果应用到的节点的距离，即要定位反射的顶部。 topopaque和bottomopaque参数指定反射的顶部和底部的不透明度(从0到1)。fraction参数指定反射中包含了多少节点(从0到1)。

```java
public void start(Stage primaryStage) {

        Text text = new Text("Reflection Effect");
        text.setLayoutX(30);
        text.setLayoutY(20);
        text.setTextOrigin(VPos.TOP);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 40));

        Reflection reflection = new Reflection();
        reflection.setTopOffset(0);
        reflection.setTopOpacity(0.75);
        reflection.setBottomOpacity(0.10);
        reflection.setFraction(0.7);

        text.setEffect(reflection);

        Scene scene = new Scene(new Pane(text), 425, 175);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
```

**Gaussian Blur**

JavaFX高斯模糊效果模糊了它应用的JavaFX节点。 JavaFX高斯模糊效果是由JavaFX .scene.effect. gaussianblur类提供的。 GaussianBlur类采用一个名为radius的参数，它决定模糊效果的“宽度”。 半径越宽，高斯模糊效果对目标节点的模糊效果就越大。 下面是一个代码示例，展示了两个带有高斯模糊效果的JavaFX文本控件，它们的半径不同，所以你可以看到它们的区别(后面会有截图):  

```java
public void start(Stage primaryStage) {

        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(5);

        Text text1 = new Text("Blur Effect 1");
        text1.setLayoutX(30);
        text1.setLayoutY(20);
        text1.setTextOrigin(VPos.TOP);
        text1.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        text1.setEffect(blur);


        GaussianBlur blur2 = new GaussianBlur();
        blur2.setRadius(10);

        Text text2 = new Text("Blur Effect 2");
        text2.setLayoutX(30);
        text2.setLayoutY(100);
        text2.setTextOrigin(VPos.TOP);
        text2.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        text2.setEffect(blur2);



        Scene scene = new Scene(new Pane(text1, text2), 425, 175);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
```

