

JavaFX Button控件允许JavaFX应用程序在应用程序用户单击按钮时执行某些操作。JavaFX Button控件由JavaFX .scene.control.Button类表示。一个JavaFX按钮可以有一个文本

### 1. 创建一个button

```java
Button button = new Button("My Label");
```

### 2. 添加button到Scene上

```java
package com.jenkov.javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ButtonExperiments extends Application  {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("HBox Experiment 1");

        Button button = new Button("My Button");

        Scene scene = new Scene(button, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```

### 3. Button 属性

**设置文本内容**

```java
button.setText("Click me if you dare!");
```

**设置文本字体大小**

您可以设置JavaFX按钮的文本大小。 你可以使用CSS属性-fx-text-size。 这个CSS属性在按钮CSS样式一节中解释  

**文字环绕**

```java
button.setWrapText(true);
```

**字体颜色**

```java
Button button = new Button("Click me!");

Font font = Font.font("Courier New", FontWeight.BOLD, 36);

button.setFont(font);
```

**默认按钮模式**

JavaFX Button可以设置为默认模式。当Button处于默认模式时，它会以不同的方式呈现，因此用户可以看到这是默认按钮。在Windows上，按钮的背景颜色会改变，尽管我猜这也取决于应用程序中使用的颜色主题等，并且可能在JavaFX的未来版本中改变。

默认按钮用于对话框或表单中的“默认选择”。因此，用户更容易选择用户最可能经常做出的选择。

对话框或表单的默认按钮有一些额外的键盘快捷键来帮助用户单击它:

Windows + Linux

* 如果没有其他按钮有焦点，按下ENTER键盘键将激活默认按钮。

* 如果默认按钮有焦点，按下ENTER键盘键将激活默认按钮。

Mac

* 只有默认按钮可以通过按ENTER键盘键激活。所有其他按钮都是通过按SPACE键盘键激活的。

* 将JavaFX按钮设置为默认按钮是通过它的setDefaultButton()方法完成的。下面是一个将JavaFX按钮设置为默认按钮的例子:

```java
button.setDefaultButton(true);
```

**取消按钮模式**

JavaFX Button可以设置为取消模式。当按钮处于取消模式时，按ESC键盘键可以更容易地激活它——如果场景图中没有其他节点使用此键。

在取消模式下设置JavaFX按钮(作为取消按钮)是通过它的setCancelButton()方法完成的。下面是在取消模式下设置JavaFX按钮的例子:

```java
buttonDefault.setCancelButton(true);
```

**按钮图片**

```java
Image image = new Image(input);
ImageView imageView = new ImageView(image);

Button button = new Button("Home", imageView);
```

**按钮大小**

```java
button.setMinWidth()
button.setMaxWidth()
button.setPrefWidth()

button.setMinHeight()
button.setMaxHeight()
button.setPrefHeight()

button.setMinSize()
button.setMaxSize()
button.setPrefSize()
```

### 4. 按钮事件

为了响应按钮的单击，需要将事件监听器附加到button对象。下面是它的样子:

```java
button.setOnAction(new EventHandler() {
    @Override
    public void handle(ActionEvent actionEvent) {
        //... do something in here.
    }
});

button.setOnAction(actionEvent ->  {
    //... do something in here.    
});
```

### 5. 按钮助记符

您可以在JavaFX Button实例上设置助记符。助记符是一种键盘键，当与ALT键一起按下时，它会激活按钮。因此，助记符是激活按钮的键盘快捷键。稍后我将解释如何通过助记符激活按钮。

按钮的助记符在按钮文本中指定。您可以通过在要设置为按钮助记符的按钮文本中的字符前面放置下划线(_)来标记要使用哪个键。下划线字符将不会显示在按钮文本中。下面是一个设置按钮助记符的例子:

```java
button.setMnemonicParsing(true);

button.setText("_Click");
```

注意，有必要首先调用值为true的按钮上的setMnemonicParsing()。这将指示按钮解析按钮文本中的助记符。如果使用false值调用此方法，按钮文本中的下划线字符将只是显示为文本，而不会被解释为助记符。

第二行设置按钮上的文本_Click。这告诉按钮使用键c作为助记符。助记符不区分大小写，所以激活按钮的不一定是大写C。

要激活按钮，你现在可以按ALT-C(同时)。这将激活按钮，就像你用鼠标点击它一样。

你也可以先按一下ALT键。它将在按钮文本中显示按钮的助记符。然后你可以按c键。如果您按ALT键，然后再按ALT键，助记符首先显示，然后再次隐藏。当助记符是可见的，你可以激活按钮与助记键单独，不需要同时按下ALT。当助记符不可见时，你必须同时按下ALT和助记符键来激活按钮。

下面是两个屏幕截图，展示了助记符不可见和可见时的样子:

### 6. 按钮的CSS样式

```CSs
-fx-border-width
-fx-border-color
-fx-background-color
-fx-font-size
-fx-text-fill
```

```java
Button button = new Button("My Button");

button.setStyle("-fx-background-color: #ff0000; ");
```

### 7. 不可点击

```java
Button button = new Button();
button.setText("Click me!");

// here the app is running, and something happens so
// the button should now be disabled.

button.setDisable(true);


// again the app runs, and something happens so
// the button should now be enabled again.

button.setDisable(false);
```

### 8. 按钮的FXML

```xml
<?xml version="1.0" encoding="UTF-8"?>
    <?import javafx.scene.layout.VBox?>
    <?import javafx.scene.control.Button?>
    <VBox xmlns:fx="http://javafx.com/fxml" spacing="20">
    <children>
        <Button fx:id="button1" text="Click me!" onAction="#buttonClicked"/>
    </children>
</VBox>
```

### 9. 按钮转变

```
button.getTransforms().add(scaleTransformation);
```

```
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class ButtonTransformationExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Button button = new Button();

        button.setText("Click me!");

        button.setOnAction((event) -> {
            System.out.println("Button clicked!");
        });

        Scale scaleTransformation = new Scale();
        scaleTransformation.setX(3.0);
        scaleTransformation.setY(2.0);
        scaleTransformation.setPivotX(0);
        scaleTransformation.setPivotY(0);

        button.getTransforms().add(scaleTransformation);

        VBox  vbox  = new VBox(button);
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.setWidth(512);
        primaryStage.setHeight(256);
        primaryStage.show();
    }

}
```