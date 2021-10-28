JavaFX Stage是JavaFX桌面程序的一个窗口，你能够在Stage里面插入Scene（窗口中显示的内容）

当JavaFX程序启动时，JavaFX应用程序会通过start()方法创建一个根Stage对象。这个Stag对象就是你程序的主窗口。当你的程序需要开启更多窗口时，你也可以在程序的生命周期内创建一个新的Stage对象。

### 1.  创建一个Stage

```java
Stage stage = new Stage();
```

### 2. Stage显示

简单的创建Stage对象是不会显示的，需要调用show()或者showAndWait()方法显示

```java
Stage stage = new Stage();
stage.show();
```

**show()**：show()使Stage可见，并立即退出show()方法

**showAndWait()**：显示Stage对象，然后阻塞在此方法，一直等待Stage关闭

### 3. 在Stage中设置Scene

```java
VBox vBox = new VBox(new Label("A JavaFX Label"));
Scene scene = new Scene(vBox);

Stage stage = new Stage();
stage.setScene(scene);
```

### 4. Stage 属性

* titile: Stage标题
* position： x,y Stage的起始坐标
* width: 宽度
* height: 高度
* modality： 窗口形态
  * Modality.APPLICATION_MODAL: 应用程序窗口
  * Modality.WINDOW_MODAL： 模态窗口
  * Modality.NONE：无
* owner: 会阻塞父级Stage
* style: 样式
  * DECORATED: 标准窗口（标题栏，最小，最大，关闭按钮以及白色背景）
  * UNDECORATED（只有白色背景）
  * TRANSPARENT（没有装饰的窗口，背景是透明的）
  * UNIFIED（类似于DECORATED，但是它没有边界）
  * UTILITY（类似于DECORATED,没有最小化按钮）

### 5. 设置全屏模式

```java
VBox vbox = new VBox();
Scene scene = new Scene(vbox);

primaryStage.setScene(scene);
primaryStage.setFullScreen(true);

primaryStage.show();
```

### 6.  Stage事件

1. 关闭事件

   ```java
   primaryStage.setOnCloseRequest((event) -> {
       System.out.println("Closing Stage");
   });
   ```

2. Stage隐藏中事件

   ```java
   primaryStage.setOnHiding((event) -> {
       System.out.println("Hiding Stage");
   });
   ```

3. Stage隐藏后事件

   ```java
   primaryStage.setOnHidden((event) -> {
       System.out.println("Stage hidden");
   });
   ```

4. Stage显示中事件

   ```java
   primaryStage.setOnShowing((event) -> {
       System.out.println("Showing Stage");
   });
   ```

5. Stage显示后事件

   ```
   primaryStage.setOnShown((event) -> {
       System.out.println("Stage Shown");
   });
   ```

6. Stage键盘点击事件

   ```java
   primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,  (event) -> {
       System.out.println("Key pressed: " + event.toString());
   
       switch(event.getCode().getCode()) {
           case 27 : { // 27 = ESC key
               primaryStage.close();
               break;
           }
           case 10 : { // 10 = Return
               primaryStage.setWidth( primaryStage.getWidth() * 2);
           }
           default:  {
               System.out.println("Unrecognized key");
           }
       }
   });
   ```



