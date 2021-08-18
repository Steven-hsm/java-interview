JavaFX MenuButton控件的工作方式与普通的JavaFX Button类似，只是它提供了一个可供用户选择单击的选项列表。这些选项中的每一个都像一个单独的按钮——这意味着你的应用程序可以侦听点击，并分别对每个选项做出响应。在某种程度上，JavaFX MenuButton的工作方式有点像JavaFX MenuBar。

JavaFX MenuButton可以显示或隐藏菜单项。菜单项通常是在菜单按钮中单击一个小箭头按钮时显示的。JavaFX MenuButton控件由JavaFX .scene.control.MenuButton类表示。

### 1. MenuButton vs. ChoiceBox and ComboBox

MenuButton看起来类似于ChoiceBox和ComboBox，但不同之处在于，MenuButton的设计是当你选择它的菜单选项之一时触发一个动作，而ChoiceBox和ComboBox的设计只是在内部记录选择了什么选项，以便以后读取。

### 2. 创建一个菜单按钮

```java
MenuItem menuItem1 = new MenuItem("Option 1");
MenuItem menuItem2 = new MenuItem("Option 2");
MenuItem menuItem3 = new MenuItem("Option 3");

MenuButton menuButton = new MenuButton("Options", null, menuItem1, menuItem2, menuItem3);
```

```java
MenuItem menuItem1 = new MenuItem("Option 1");
MenuItem menuItem2 = new MenuItem("Option 2");
MenuItem menuItem3 = new MenuItem("Option 3");

FileInputStream input = new FileInputStream("resources/images/iconmonstr-menu-5-32.png");
Image image = new Image(input);
ImageView imageView = new ImageView(image);

MenuButton menuButton = new MenuButton("Options", imageView, menuItem1, menuItem2, menuItem3);
```

### 3. 在Scene上显示菜单按钮

```java
package com.jenkov.javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileInputStream;


public class MenuButtonExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ImageView Experiment 1");


        MenuItem menuItem1 = new MenuItem("Option 1");
        MenuItem menuItem2 = new MenuItem("Option 2");
        MenuItem menuItem3 = new MenuItem("Option 3");

        MenuButton menuButton = new MenuButton("Options", null, menuItem1, menuItem2, menuItem3);

        HBox hbox = new HBox(menuButton);

        Scene scene = new Scene(hbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```

### 4. 菜单按钮字体

```java
MenuItem menuItem1 = new MenuItem("Option 1");
MenuItem menuItem2 = new MenuItem("Option 2");

MenuButton menuButton = new MenuButton("Options", null, menuItem1, menuItem2);

Font font = Font.font("Courier New", FontWeight.BOLD, 36);
menuButton.setFont(font);
```

### 5. 菜单按钮设置图标

```java
  menuButton.setGraphic(imageView);
```

### 6. 菜单选择响应事件

```java
MenuItem menuItem3 = new MenuItem("Option 3");

menuItem3.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
        System.out.println("Option 3 selected");
    }
});
```

```java
menuItem3.setOnAction(event -> {
    System.out.println("Option 3 selected via Lambda");
});
```