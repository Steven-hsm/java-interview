JavaFX AreaChart可以在JavaFX应用程序中绘制区域图。 面积图是折线图，在折线下面的区域是用颜色绘制的。 JavaFX AreaChart组件由JavaFX .scene.chart.AreaChart类表示。  

### 1. X轴和Y轴

```java
NumberAxis xAxis = new NumberAxis();
xAxis.setLabel("No of employees");

NumberAxis yAxis = new NumberAxis();
yAxis.setLabel("Revenue per employee");
```

### 2. 创建一个AreaChart

```java
NumberAxis xAxis = new NumberAxis();
xAxis.setLabel("No of employees");

NumberAxis yAxis = new NumberAxis();
yAxis.setLabel("Revenue per employee");

AreaChart areaChart = new AreaChart(xAxis, yAxis);
```

**添加数据到AreaChart**

```java
XYChart.Series dataSeries1 = new XYChart.Series();
dataSeries1.setName("2014");

dataSeries1.getData().add(new XYChart.Data( 1, 567));
dataSeries1.getData().add(new XYChart.Data( 5, 612));
dataSeries1.getData().add(new XYChart.Data(10, 800));
dataSeries1.getData().add(new XYChart.Data(20, 780));
dataSeries1.getData().add(new XYChart.Data(40, 810));
dataSeries1.getData().add(new XYChart.Data(80, 850));

areaChart.getData().add(dataSeries1);
```

### 3. 添加LineChart到Scene

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AreaChartExperiments extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("AreaChart Experiments");

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("No of employees");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Revenue per employee");

        AreaChart areaChart = new AreaChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("2014");

        dataSeries1.getData().add(new XYChart.Data( 1, 567));
        dataSeries1.getData().add(new XYChart.Data( 5, 612));
        dataSeries1.getData().add(new XYChart.Data(10, 800));
        dataSeries1.getData().add(new XYChart.Data(20, 780));
        dataSeries1.getData().add(new XYChart.Data(40, 810));
        dataSeries1.getData().add(new XYChart.Data(80, 850));

        areaChart.getData().add(dataSeries1);

        VBox vbox = new VBox(areaChart);

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