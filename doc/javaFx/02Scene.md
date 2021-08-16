Scenne中包含所有的JavaFX GUI组件，Scene必须设置在Stage上才可以被可视化。

### 1. 创建一个Scene

```java
VBox  vBox  = new VBox();
Scene scene = new Scene(vBox);
```

### 2. 在Stage上显示Scene

```shell
VBox vBox = new VBox(new Label("A JavaFX Label"));
Scene scene = new Scene(vBox);

Stage stage = new Stage();
stage.setScene(scene);
```

Scene一次只能和一个单独的Stage,一个Stage同时也只能展示一个Scene

### 3. 设置鼠标光标

```java
scene.setCursor(Cursor.OPEN_HAND);
```

- Cursor.OPEN_HAND
- Cursor.CLOSED_HAND
- Cursor.CROSSHAIR
- Cursor.DEFAULT
- Cursor.HAND
- Cursor.WAIT
- Cursor.H_RESIZE
- Cursor.V_RESIZE
- Cursor.MOVE
- Cursor.TEXT

