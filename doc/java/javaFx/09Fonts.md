JavaFX字体类，java.scene.text。字体，使您能够加载不同的字体以在JavaFX应用程序中使用。字体是一种文本样式。所有用相同字体呈现的文本看起来都很相似。在这个JavaFX字体教程中，我将向你展示如何在JavaFX中加载和设置字体。

### 1. 创建字体实例

要在JavaFX中使用字体，必须创建JavaFX字体实例。创建JavaFX Font实例的最简单方法是使用Font类中的静态工厂方法。下面的例子展示了如何使用字体类静态工厂方法的许多变体来创建JavaFX字体实例:

```java
String      fontFamily  = "Arial";
double      fontSize    = 13;
FontWeight  fontWeight  = FontWeight.BOLD;
FontPosture fontPosture = FontPosture.ITALIC;

Font font1 = Font.font(fontFamily);
Font font2 = Font.font(fontSize);
Font font3 = Font.font(fontFamily, fontSize);
Font font4 = Font.font(fontFamily, fontWeight , fontSize);
Font font5 = Font.font(fontFamily, fontPosture, fontSize);
Font font6 = Font.font(fontFamily, fontWeight , fontPosture, fontSize);
```

如您所见，Font工厂方法使您能够创建代表不同字体族、字体大小、字体权重和字体姿势的Font实例。

### 2. 使用Font实例

```java
Font font = Font.font("Arial");

Text text = new Text("This is the text");
text.setFont(font);
```

### 3. 列出安装的字体

```java
List<String> fontFamilies = Font.getFamilies();
List<String> fontNames    = Font.getFontNames();
```

```java
for(String item : fontFamilies) {
    System.out.println(item);
}
for(String item : fontNames) {
    System.out.println(item);
}
```