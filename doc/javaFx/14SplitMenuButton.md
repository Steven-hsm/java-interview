JavaFX SplitMenuButton控件可以显示一个菜单选项列表，用户可以从中选择，也可以显示一个按钮，用户可以在选择菜单选项时单击该按钮。JavaFX SplitMenuButton可以显示或隐藏菜单项。当在SplitMenuButton中单击一个小箭头按钮时，通常会显示菜单项。JavaFX SplitMenuButton控件由JavaFX .scene.control.SplitMenuButton类表示。

### 1. 创建一个SplitMenuButton

```java
SplitMenuButton splitMenuButton = new SplitMenuButton();
```

### 2. 设置属性

**文本**

```java
splitMenuButton.setText("Click here!");
```

**字体**

```java
SplitMenuButton splitMenuButton = new SplitMenuButton();

Font font = Font.font("Courier New", FontWeight.BOLD, 36);
splitMenuButton.setFont(font);
```

### 3. 子菜单选项

```java
MenuItem choice1 = new MenuItem("Choice 1");
MenuItem choice2 = new MenuItem("Choice 2");
MenuItem choice3 = new MenuItem("Choice 3");

button.getItems().addAll(choice1, choice2, choice3);
```

### 4. 子菜单选择事件

```java
MenuItem choice1 = new MenuItem("Choice 1");
MenuItem choice2 = new MenuItem("Choice 2");
MenuItem choice3 = new MenuItem("Choice 3");

choice1.setOnAction((e)-> {
    System.out.println("Choice 1 selected");
});
choice2.setOnAction((e)-> {
    System.out.println("Choice 2 selected");
});
choice3.setOnAction((e)-> {
    System.out.println("Choice 3 selected");
});
```

### 5. 按钮点击事件

```java
splitMenuButton.setOnAction((e) -> {
    System.out.println("SplitMenuButton clicked!");
});
```

### 6. SplitMenuButton vs. MenuButton, ChoiceBox and ComboBox

你可能想知道JavaFX SplitMenuButton和JavaFX MenuButton、JavaFX ChoiceBox和JavaFX ComboBox之间的区别。我将在下面解释这一点。

SplitMenuButton和MenuButton控件都是按钮。这意味着，它们是为了让应用程序响应对菜单项之一的单击，或者在SplitMenuButton的情况下——主按钮或菜单项之一。当您希望在用户单击/选择菜单项时立即执行操作时，请使用这两个控件之一。使用SplitMenuButton时，其中一个选择做得比其他的更频繁。将按钮部分用于最常用的选择，将菜单项用于不常用的选择。

ChoiceBox和ComboBox只是在内部存储用户在菜单项中所做的选择。它们不是为菜单项选择时的立即操作而设计的。在表单中使用这些控件，用户在最终单击“确定”或“取消”按钮之前必须做出几个选择。当点击这些按钮时，您可以从选择框或组合框中读取选择的菜单项。