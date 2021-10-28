JavaFX PieChart组件能够根据提供的数据在JavaFX应用程序中绘制饼图。PieChart组件非常容易使用。JavaFX PieChart组件由JavaFX .scene.chart.PieChart类表示。

### 1. 创建一个PieChart

```java
PieChart pieChart = new PieChart();
```

### 2. 添加数据到PieChart

```java
PieChart pieChart = new PieChart();

PieChart.Data slice1 = new PieChart.Data("Desktop", 213);
PieChart.Data slice2 = new PieChart.Data("Phone"  , 67);
PieChart.Data slice3 = new PieChart.Data("Tablet" , 36);

pieChart.getData().add(slice1);
pieChart.getData().add(slice2);
pieChart.getData().add(slice3);
```

### 3. 添加PieChart到Scene

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PieChartExperiments extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("My First JavaFX App");

        PieChart pieChart = new PieChart();

        PieChart.Data slice1 = new PieChart.Data("Desktop", 213);
        PieChart.Data slice2 = new PieChart.Data("Phone"  , 67);
        PieChart.Data slice3 = new PieChart.Data("Tablet" , 36);

        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
        pieChart.getData().add(slice3);

        VBox vbox = new VBox(pieChart);

        Scene scene = new Scene(vbox, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(1200);

        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```