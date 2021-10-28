JavaFX FXML是一种XML格式，它使您能够以类似于使用HTML编写web gui的方式编写JavaFX gui。因此，FXML使您能够将JavaFX布局代码与应用程序代码的其余部分分开。这将清理布局代码和应用程序代码的其余部分。

FXML既可以用于组成整个应用程序GUI的布局，也可以用于组成应用程序GUI的一部分，例如窗体、标签页、对话框等的一部分布局。

### 1. FXML例子

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Label?>

<VBox>
    <children>
        <Label text="Hello world FXML"/>
    </children>
</VBox>
```

这个例子定义了一个VBox，其中包含一个Label作为子元素。VBox组件是一个JavaFX布局组件。Label只是在GUI中显示一个文本。如果您还不了解所有的JavaFX组件，请不要担心。一旦你开始和他们一起玩，你就会。

FXML文档中的第一行是XML文档的标准第一行。

下面两行是import语句。在FXML中，您需要导入想要使用的类。FXML中使用的JavaFX类和核心Java类都必须导入。

在导入语句之后，您就拥有了GUI的实际组成。声明了一个VBox组件，在它的子属性中声明了一个Label组件。结果是Label实例将被添加到VBox实例的子属性中。

### 2. 加载FXML文件

为了加载FXML文件并创建文件声明的JavaFX GUI组件，需要使用FXMLLoader (JavaFX .FXML .FXMLLoader)类。下面是一个完整的JavaFX FXML加载示例，它加载一个FXML文件并返回其中声明的JavaFX GUI组件:

```java
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class FXMLExample extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:///C:/data/hello-world.fxml"));
        VBox vbox = loader.<VBox>load();

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
```

### 3. 在fxml中导入类

```fxml
<?import javafx.scene.layout.VBox?>
```

### 4. 在fxml中创建对象

FXML既可以创建JavaFX GUI对象，也可以创建非JavaFX对象。在FXML中有几种创建对象的方法。在下面的部分中，我们将看到这些选项是什么。

#### 4.1  FXML元素和无参构造器

在FXML中创建对象的最简单方法是通过FXML文件中的FXML元素。FXML中使用的元素名与Java类名相同，但没有包名。一旦通过FXML导入语句导入了类，就可以使用它的名称作为FXML元素名称。

在下面的例子中，元素名VBox和Label是有效的，因为这两个类是在FXML文件的前面用import语句声明的:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Label?>

<VBox>
    <children>
        <Label text="Hello world FXML"/>
    </children>
</VBox>
```

#### 4.2 通过valueIOf()方法

在FXML中创建对象的另一种方法是在要创建对象的类中调用静态valueOf()方法。通过valueOf()方法创建对象的方法是在FXML元素中插入一个value属性。下面是一个例子:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import com.jenkov.javafx.MyClass?>

<MyClass value="The Value"/>
```

```java
public MyClass {
    public static MyClass valueOf(String value) {
        return new MyClass(value);
    }

    private String value = null;

    public MyClass(String value) {
        this.value = value;
    }
}
```

注意静态的valueOf()方法，它接受一个Java String作为参数。当FXMLLoader在FXML文件中看到MyClass元素时，该方法将被FXMLLoader调用。valueOf()方法返回的对象是插入到FXML文件中组成的GUI中的对象。上面的FXML除了MyClass元素之外不包含任何其他元素，但它可以。

请记住，无论valueOf()方法返回什么对象，都将在Scene(组合GUI)中使用。如果返回的对象不是包含valueOf()方法的类的实例，而是其他类的实例，那么该对象仍将在对象图中使用。元素名仅用于查找包含valueOf()方法的类(当FXML元素包含value属性时)。

#### 4.3 通过工厂方法创建队形

从某种意义上说，valueOf()方法也是一个基于String参数创建对象的工厂方法。但是-你也可以让FXMLLoader调用其他工厂方法而不是valueOf()方法。

要调用另一个工厂方法来创建对象，需要插入fx:factory属性。factory属性的值应该是要调用的工厂方法的名称。下面是一个例子:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import com.jenkov.javafx.MyClass?>

<MyClass fx:factory="instance"/>
```

```java
public MyClass {
    public static MyClass instance() {
        return new MyClass();
    }
}
```

请注意instance()方法。这个方法可以从上面的FXML代码片段中的fx:factory属性中引用。

注意，工厂方法必须是一个无参数方法，才能从fx:factory属性调用它。

### 5. FXML属性

一些JavaFX对象有属性。事实上，大多数都是这样。可以通过两种方式设置属性值。第一种方法是使用XML属性来设置属性值。第二种方法是使用嵌套的XML元素来设置属性值。

为了更好地理解如何在FXML元素中设置属性，让我们看一个例子:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Label?>

<VBox spacing="20">
    <children>
        <Label text="Line 1"/>
        <Label text="Line 2"/>
    </children>       
</VBox>
```

