JavaFX ScatterChart组件可以在JavaFX应用程序中绘制散点图。 JavaFX ScatterChart组件由JavaFX .scene.chart.ScatterChart类表示。  

### 1. X轴和Y轴

```java
NumberAxis xAxis = new NumberAxis();
xAxis.setLabel("No of employees");

NumberAxis yAxis = new NumberAxis();
yAxis.setLabel("Revenue per employee");
```

### 2.创建Scatter

```java
NumberAxis xAxis = new NumberAxis();
xAxis.setLabel("No of employees");

NumberAxis yAxis = new NumberAxis();
yAxis.setLabel("Revenue per employee");

ScatterChart scatterChart = new ScatterChart(xAxis, yAxis);
```

**添加数据**

```java
XYChart.Series dataSeries1 = new XYChjavaart.Series();
dataSeries1.setName("2014");

dataSeries1.getData().add(new XYChart.Data( 1, 567));
dataSeries1.getData().add(new XYChart.Data( 5, 612));
dataSeries1.getData().add(new XYChart.Data(10, 800));
dataSeries1.getData().add(new XYChart.Data(20, 780));
dataSeries1.getData().add(new XYChart.Data(40, 810));
dataSeries1.getData().add(new XYChart.Data(80, 850));

scatterChart.getData().add(dataSeries1);
```

### 3.  添加ScatterChart到Scene

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScatterChartExperiments extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("BarChart Experiments");

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("No of employees");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Revenue per employee");

        ScatterChart scatterChart = new ScatterChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("2014");

        dataSeries1.getData().add(new XYChart.Data( 1, 567));
        dataSeries1.getData().add(new XYChart.Data( 5, 612));
        dataSeries1.getData().add(new XYChart.Data(10, 800));
        dataSeries1.getData().add(new XYChart.Data(20, 780));
        dataSeries1.getData().add(new XYChart.Data(40, 810));
        dataSeries1.getData().add(new XYChart.Data(80, 850));

        scatterChart.getData().add(dataSeries1);

        VBox vbox = new VBox(scatterChart);

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