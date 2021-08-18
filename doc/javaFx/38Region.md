JavaFX Region类是所有JavaFX布局窗格(如Pane等)的基类。JavaFX Region类有一组属性和特征，这些属性和特征由所有扩展Region的JavaFX布局类共享。

### 1. Region继承图

​	![JavaFX Region class hierarchy](http://tutorials.jenkov.com/images/java-javafx/javafx-region-0.png)

### 2.Region属性

- Background 背景
- Content Area 内容区域
- Padding 内边距
- Borders 边框
- Margin 外边距
- Region Insets 插图

**设置内边距**

```java
HBox hBox = new HBox(new Label("Hello Pane"));=
hBox.setPadding(new Insets(10));
```

**边框**

笔画边框

```java
Pane  pane  = new Pane();

StrokeType     strokeType     = StrokeType.INSIDE;
StrokeLineJoin strokeLineJoin = StrokeLineJoin.MITER;
StrokeLineCap  strokeLineCap  = StrokeLineCap.BUTT;
double         miterLimit     = 10;
double         dashOffset     = 0;
List<Double>   dashArray      = null;

BorderStrokeStyle borderStrokeStyle =
        new BorderStrokeStyle(
                strokeType,
                strokeLineJoin,
                strokeLineCap,
                miterLimit,
                dashOffset,
                dashArray
        );


BorderStroke borderStroke =
        new BorderStroke(
                Color.valueOf("08ff80"),
                borderStrokeStyle,
                new CornerRadii(0),
                new BorderWidths(8)
        );

Border border = new Border(borderStroke);

pane.setBorder(border);
```

**设置背景色**

```java
Pane  pane  = new Pane();

BackgroundFill backgroundFill1 =
        new BackgroundFill(
                Color.valueOf("#ff00ff"),
                new CornerRadii(0),
                new Insets(0)
                );

BackgroundFill backgroundFill2 =
        new BackgroundFill(
                Color.valueOf("#00ff00"),
                new CornerRadii(10),
                new Insets(10)
                );

Background background =
        new Background(backgroundFill1, backgroundFill2);

pane.setBackground(background);
```

### 设置背景图片

```java
Pane pane = new Pane();

String filePath = "data/background.jpg";
Image image = null;
try {
    image = new Image(new FileInputStream(filePath));
} catch (FileNotFoundException e) {
    e.printStackTrace();
}

BackgroundImage backgroundImage =
    new BackgroundImage(
        image,
        BackgroundRepeat.NO_REPEAT,  // repeat X
        BackgroundRepeat.NO_REPEAT,  // repeat Y
        BackgroundPosition.CENTER,   // position
        new BackgroundSize(
            100,   // width  = 100%
            100,   // height = 100%
            true,  // width is percentage
            true,  // height is percentage
            true,  // contain image within bounds
            true   // cover all of Region content area
        )
    );

pane.setBackground(new Background(backgroundImage));
```