要在JavaFX中使用颜色，你有JavaFX Paint, Color, imageppattern, LinearGradient和RadialGradient。 JavaFX Paint类是JavaFX Color, imageppattern, LinearGradient和RadialGradient类的超类。 JavaFX的颜色类都在JavaFX包JavaFX .scene.paint中。 下面是JavaFX颜色类的层次结构:  

- Paint
  - Color
  - ImagePattern
  - LinearGradient
  - RadialGradient

### 1. Paint

JavaFX Paint类是JavaFX Color, imageppattern, LinearGradient和RadialGradient类的超类。 你可以直接使用Paint类从CSS颜色字符串创建Paint实例。 有关CSS颜色的更多信息，请参阅JavaFX CSS教程。 下面是一个使用Paint类的静态工厂方法创建JavaFX Paint实例的例子:  

```java
Paint paint = Paint.valueOf("blue");
```

### 2. Color

JavaFX Color类表示一种纯(统一)色。 要创建JavaFX Color实例，可以使用它的构造函数。 JavaFX Color构造函数有4个参数:  

- Red
- Green
- Blue
- Alpha

红色、绿色和蓝色参数是在最终颜色中使用的红色、绿色和蓝色色调的数量。 Alpha参数，也称为不透明度参数，指定最终颜色的不透明度。 每个参数的值介于0.0和1.0之间。  

```java
double red   = 1.0;
double green = 0.8;
double blue  = 0.6;
double alpha = 1.0;

Color color = new Color(red, green, blue, alpha);
```

JavaFX Color类还有一组静态工厂方法，可以帮助您使用各种不同的参数创建Color实例。 这些工厂方法如下面的JavaFX颜色工厂方法示例所示:  

```java
Color color1  = Color.web("#ff00ff");
Color color2  = Color.web("#ff00ff", 0.5);

Color color3  = Color.rgb(255, 0, 255);
Color color4  = Color.rgb(255, 0, 255, 0.5);

Color color5  = Color.grayRgb(255);
Color color6  = Color.grayRgb(255, 0.5);

Color color7  = Color.hsb(1.0, 0.5, 0.8);
Color color8  = Color.hsb(1.0, 0.5, 0.8, 0.5);

Color color9  = Color.color(1.0, 0.0, 1.0);
Color color10 = Color.color(1.0, 0.0, 1.0, 0.5);
```

如您所见，每个方法都有两个版本。 一个是颜色值本身，另一个是颜色值+ alpha值(不透明度)。  

Color.web()方法基于传统的web颜色代码创建一个Color实例，这在CSS Colors中也使用过。  

Color.rgb()方法用红色、绿色和蓝色的颜色值创建一个Color实例。  

Color. grayrgb()方法创建一个表示灰色颜色的Color实例。 红色、绿色和蓝色将被设置为相同的值，结果是灰色。  

Color. HSB()方法基于色相、饱和度和亮度(HSB)创建一个Color实例。 有时这也被提到HSL颜色-色相，饱和度和亮度(而不是HSB)。  

Color. Color()方法创建Color的方式类似于标准的Color构造函数。  