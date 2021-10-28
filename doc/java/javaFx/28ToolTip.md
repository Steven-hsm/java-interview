当用户将鼠标悬停在JavaFX控件上时，JavaFX工具提示类(JavaFX .scene.control.Tooltip)可以显示一个带有解释性文本的小弹出框。工具提示是现代桌面和web gui的一个众所周知的特性。工具提示在gui中提供额外的帮助文本非常有用，因为在gui中没有足够的空间让解释性文本一直可见，例如按钮文本。

### 1. 创建一个ToolTip

```java
Tooltip tooltip1 = new Tooltip("Creates a new file");
```

### 2. 添加ToolTip到JavaFX组件中

```java
Tooltip tooltip1 = new Tooltip("Creates a new file");

Button button1 = new Button("New");
button1.setTooltip(tooltip1);
```

### 3. 文本位置

```java
tooltip1.setTextAlignment(TextAlignment.LEFT);
```

- `LEFT` 居左
- `RIGHT 居右`
- `CENTER 居中`
- `JUSTIFY 自动调整`

### 4. 图标

```java
tooltip1.setGraphic(new ImageView("file:iconmonstr-basketball-1-16.png"));
```

