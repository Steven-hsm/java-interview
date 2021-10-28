JavaFX ImageView控件可以在JavaFX GUI中显示图像。ImageView控件必须添加到场景图中才能可见。JavaFX ImageView控件由JavaFX .scene.image.ImageView类表示。

### 1. 创建一个ImageView

```java
FileInputStream input = new FileInputStream("resources/images/iconmonstr-home-6-48.png");
Image image = new Image(input);
ImageView imageView = new ImageView(image);
```

首先创建一个FileInputStream，它指向要显示的图像的图像文件。

然后创建一个Image实例，将FileInputStream作为参数传递给Image构造函数。这样Image类就知道从哪里加载图像文件。

第三，创建一个ImageView实例，将Image实例作为参数传递给ImageView构造函数。

### 2. 将ImageView添加到Scene

要使ImageViewl可见，必须将其添加到Scene中。这意味着将它添加到Scene对象中。因为ImageView不是javafx.scene.Parent的子类，所以不能直接添加到Scene中。它必须嵌套在另一个组件中，例如布局组件。

```java
package com.jenkov.javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class ImageViewExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ImageView Experiment 1");

        FileInputStream input = new FileInputStream("resources/images/iconmonstr-home-6-48.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);

        HBox hbox = new HBox(imageView);

        Scene scene = new Scene(hbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
```