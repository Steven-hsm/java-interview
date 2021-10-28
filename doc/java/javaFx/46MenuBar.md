JavaFX MenuBar为JavaFX应用程序提供了一个可视化的下拉菜单，类似于大多数桌面应用程序在其应用程序窗口顶部所拥有的菜单。JavaFX MenuBar由JavaFX .scene.control.MenuBar类表示。

### 1. 创建一个MenuBar

```java
MenuBar menuBar = new MenuBar();
```

### 2. 添加MenuBar到Scene

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        Menu menu1 = new Menu("Menu 1");
        Menu menu2 = new Menu("Menu 2");
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);

        VBox vBox = new VBox(menuBar);

        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
```

### 3. 菜单图标

```java
Menu menu = new Menu("Menu 1");
menu.setGraphic(new ImageView("file:volleyball.png"));
```

### 4. 菜单事件

- `onShowing`
- `onShown`
- `onHiding`
- `onHidden`

```java
Menu menu = new Menu("Menu 1");

menu.setOnShowing(e -> { System.out.println("Showing Menu 1"); });
menu.setOnShown  (e -> { System.out.println("Shown Menu 1"); });
menu.setOnHiding (e -> { System.out.println("Hiding Menu 1"); });
menu.setOnHidden (e -> { System.out.println("Hidden Menu 1"); });
```

### 5. 添加子菜单列表

```java
Menu menu = new Menu("Menu 1");
MenuItem menuItem1 = new MenuItem("Item 1");
MenuItem menuItem2 = new MenuItem("Item 2");

menu.getItems().add(menuItem1);
menu.getItems().add(menuItem2);

MenuBar menuBar = new MenuBar();
menuBar.getMenus().add(menu);
```

### 6. 子菜单图像

```java
Menu menu = new Menu("Menu 1");

MenuItem menuItem1 = new MenuItem("Item 1");
menuItem1.setGraphic(new ImageView("file:soccer.png"));

MenuItem menuItem2 = new MenuItem("Item 2");
menuItem1.setGraphic(new ImageView("file:basketball.png"));

menu.getItems().add(menuItem1);
menu.getItems().add(menuItem2);

MenuBar menuBar = new MenuBar();
menuBar.getMenus().add(menu);
```

### 7. 子菜单事件

```java
MenuItem menuItem1 = new MenuItem("Item 1");

menuItem1.setOnAction(e -> {
    System.out.println("Menu Item 1 Selected");
});
```

### 8. 子菜单

```java
Menu menu = new Menu("Menu 1");

Menu subMenu = new Menu("Menu 1.1");
MenuItem menuItem11 = new MenuItem("Item 1.1.1");
subMenu.getItems().add(menuItem11);
menu.getItems().add(subMenu);

MenuItem menuItem1 = new MenuItem("Item 1");
menu.getItems().add(menuItem1);

MenuItem menuItem2 = new MenuItem("Item 2");
menu.getItems().add(menuItem2);

MenuBar menuBar = new MenuBar();
menuBar.getMenus().add(menu);
```

### 9. 选中子菜单

```java
CheckMenuItem checkMenuItem = new CheckMenuItem("Check this!");

menu.getItems().add(checkMenuItem);
```

### 10. 单选子菜单项目

```java
Menu menu = new Menu("Menu 1");

RadioMenuItem choice1Item = new RadioMenuItem("Choice 1");
RadioMenuItem choice2Item = new RadioMenuItem("Choice 2");
RadioMenuItem choice3Item = new RadioMenuItem("Choice 3");

ToggleGroup toggleGroup = new ToggleGroup();
toggleGroup.getToggles().add(choice1Item);
toggleGroup.getToggles().add(choice2Item);
toggleGroup.getToggles().add(choice3Item);

menu.getItems().add(choice1Item);
menu.getItems().add(choice2Item);
menu.getItems().add(choice3Item);

MenuBar menuBar = new MenuBar();
menuBar.getMenus().add(menu);
```

### 11.菜单选项分离

```java
MenuItem item1 = new MenuItem("Item 1");
MenuItem item2 = new MenuItem("Item 2");
SeparatorMenuItem separator = new SeparatorMenuItem();

menu.getItems().add(item1);
menu.getItems().add(separator);
menu.getItems().add(item2);

MenuBar menuBar = new MenuBar();
menuBar.getMenus().add(menu);
```

### 12. 经典控制菜单选项

```java
Menu menu = new Menu("Menu 1");

Slider slider = new Slider(0, 100, 50);

CustomMenuItem customMenuItem = new CustomMenuItem();
customMenuItem.setContent(slider);
customMenuItem.setHideOnClick(false);
menu.getItems().add(customMenuItem);

Button button = new Button("Custom Menu Item Button");
CustomMenuItem customMenuItem2 = new CustomMenuItem();
customMenuItem2.setContent(button);
customMenuItem2.setHideOnClick(false);
menu.getItems().add(customMenuItem2);

MenuBar menuBar = new MenuBar();
menuBar.getMenus().add(menu);
```



