JavaFX Pagination控件允许用户在内容中逐页导航，例如包含搜索结果、文章、图像或类似类型内容的页面。JavaFX Pagination控件由类JavaFX .scene.control.Pagination表示。

### 1. Pagination 属性

- Page count
- Current page index
- Max number of page indicators

```java
Pagination pagination = new Pagination();

pagination.setPageCount(21);
pagination.setCurrentPageIndex(3);
pagination.setMaxPageIndicatorCount(3);
```

### 2. 分页工厂方法

JavaFX Pagination控件需要在其上设置一个页面工厂，以便能够在分页内容中正确导航。当用户导航到新页面时，将调用页面工厂。页面工厂组件通过其setPageFactory()方法附加到Pagination控件，并且必须实现接口javafx.util.Callback接口。

```java
public interface Callback<P,R> {
    public R call(P param);
}
```

在setPageFactory()方法中，两个类型参数P和R被设置为Integer (P)和Node (R)。这意味着，页面工厂必须实现Callback<Integer, Node>接口。下面是Callback<Integer, Node>的实现示例:

```java
public static class MyPageFactory implements Callback<Integer, Node> {
    @Override
    public Node call(Integer pageIndex) {
        return new Label("Content for page " + pageIndex);
    }
}
```

传递给Callback实现的Integer参数是页面工厂应该为其创建Node的页面的索引。返回的Node应该显示具有给定页面索引的页面的内容。

下面是一个在JavaFX Pagination控件上设置页面工厂的例子:

```java
pagination.setPageFactory(new MyPageFactory());
```

**匿名方法**

```java
pagination.setPageFactory(new Callback<Integer, Node>() {
    @Override
    public Node call(Integer pageIndex) {
        return new Label("Content for page " + pageIndex);
    }
});
```

### 3.完整例子

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PaginationExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        Pagination pagination = new Pagination();
        pagination.setPageCount(21);
        pagination.setCurrentPageIndex(3);
        pagination.setMaxPageIndicatorCount(3);

        pagination.setPageFactory((pageIndex) -> {

            Label label1 = new Label("Content for page with index: " + pageIndex);
            label1.setFont(new Font("Arial", 24));

            Label label2 = new Label("Main content of the page ...");

            return new VBox(label1, label2);
        });

        VBox vBox = new VBox(pagination);
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
```

