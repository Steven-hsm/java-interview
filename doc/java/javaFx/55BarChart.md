JavaFX BarChart组件能够在JavaFX应用程序中绘制条形图。 这在类似仪表板的应用程序中非常有用。 JavaFX BarChart组件由JavaFX .scene.chart.BarChart类表示  

### 1. X轴和Y轴

JavaFX BarChart绘制柱状图。 条形图是一个二维图形，意思是这个图形有一个X轴和一个Y轴。 对于条形图，X轴通常是某种类别，而Y轴是数值。  

例如，想象一个柱状图，显示了桌面、手机和平板电脑用户对一个网站的访问次数。 柱状图显示3个柱状图。 X轴上的类别是“桌面”、“手机”和“平板”。 Y轴表示X轴上每个类别的访问次数，所以Y轴是数值。  

您需要定义BarChart使用的X轴和Y轴。 分类轴由JavaFX类JavaFX .scene.chart. categoryaxis表示。 数值轴由JavaFX类JavaFX .scene.chart. numberaxis表示。  

```java
CategoryAxis xAxis    = new CategoryAxis();
xAxis.setLabel("Devices");

NumberAxis yAxis = new NumberAxis();
yAxis.setLabel("Visits");
```

### 2. 创建一个BarChart

```java
CategoryAxis xAxis    = new CategoryAxis();
xAxis.setLabel("Devices");

NumberAxis yAxis = new NumberAxis();
yAxis.setLabel("Visits");

BarChart     barChart = new BarChart(xAxis, yAxis);
```

### 3. 添加数据到BarChart

```java
XYChart.Series dataSeries1 = new XYChart.Series();
dataSeries1.setName("2014");

dataSeries1.getData().add(new XYChart.Data("Desktop", 178));
dataSeries1.getData().add(new XYChart.Data("Phone"  , 65));
dataSeries1.getData().add(new XYChart.Data("Tablet"  , 23));


barChart.getData().add(dataSeries1);
```

#### 4. 添加BarChart到Scene

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BarChartExperiments extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("BarChart Experiments");

        CategoryAxis xAxis    = new CategoryAxis();
        xAxis.setLabel("Devices");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Visits");

        BarChart     barChart = new BarChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("2014");

        dataSeries1.getData().add(new XYChart.Data("Desktop", 567));
        dataSeries1.getData().add(new XYChart.Data("Phone"  , 65));
        dataSeries1.getData().add(new XYChart.Data("Tablet"  , 23));

        barChart.getData().add(dataSeries1);

        VBox vbox = new VBox(barChart);

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

### 5.展示多条数据

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BarChartExperiments extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("BarChart Experiments");


        CategoryAxis xAxis    = new CategoryAxis();
        xAxis.setLabel("Devices");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Visits");

        BarChart     barChart = new BarChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("2014");

        dataSeries1.getData().add(new XYChart.Data("Desktop", 567));
        dataSeries1.getData().add(new XYChart.Data("Phone"  , 65));
        dataSeries1.getData().add(new XYChart.Data("Tablet"  , 23));

        barChart.getData().add(dataSeries1);

        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("2015");

        dataSeries2.getData().add(new XYChart.Data("Desktop", 540));
        dataSeries2.getData().add(new XYChart.Data("Phone"  , 120));
        dataSeries2.getData().add(new XYChart.Data("Tablet"  , 36));

        barChart.getData().add(dataSeries2);

        VBox vbox = new VBox(barChart);

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