JavaFX ScrollPane控件是一个容器，如果组件大于ScrollPane的可见区域，则在它所包含的组件周围有两个滚动条。滚动条允许用户在ScrollPane中显示的组件周围滚动，因此可以看到组件的不同部分。JavaFX ScrollPane控件由JavaFX类JavaFX .scene.control.ScrollPane表示。

### 1. 创建一个ScrollPane

```java
ScrollPane scrollPane = new ScrollPane();
```

### 2. 设置ScrollPane内容

```java
ScrollPane scrollPane = new ScrollPane();

String imagePath = "images/aerial-beverage-caffeine-972533.jpg";
ImageView imageView = new ImageView(new Image(new FileInputStream(imagePath)));

scrollPane.setContent(imageView);
```

### 3. 横向扩展

​	默认情况下，用户只能使用滚动条在JavaFX ScrollPane中显示的内容周围导航。但是，可以使JavaFX ScrollPane变为可平移的。可移动的ScrollPane使用户可以通过按住鼠标左键并移动鼠标来导航其内容。这将具有与使用滚动条相同的效果。但是，使用平移可以同时沿着X轴和Y轴移动内容。使用滚动条是不可能的，因为用户一次只能操作一个滚动条。

要将JavaFX ScrollPane切换到可平移模式，必须将其pannableProperty设置为true。

```java
scrollPane.pannableProperty().set(true);
```

### 4. 适应宽度

```java
scrollPane.fitToWidthProperty().set(true);
```

### 5. 适应高度

```java
scrollPane.fitToHeightProperty().set(true);
```

### 6. 通过滚动条策略显示和隐藏滚动条

```java
scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
```

