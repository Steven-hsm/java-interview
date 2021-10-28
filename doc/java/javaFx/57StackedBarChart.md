JavaFX StackedBarChart组件能够在JavaFX应用程序中绘制堆叠条形图。 这在类似仪表板的应用程序中非常有用。 JavaFX StackedBarChart组件由JavaFX .scene.chart.StackedBarChart类表示。  

### 1. X轴和Y轴

JavaFX的堆栈条形图绘制了一个堆栈条形图。 堆叠条形图是一个二维图，这意味着这个图有一个X轴和一个Y轴。 对于堆叠条形图，X轴通常是某种类别，而Y轴是数值。  

例如，想象一个堆叠的柱状图，展示了2014年和2015年从台式机、手机和平板电脑用户访问一个网站的数量。 堆叠的柱状图会显示2个柱状图。 一个条形图显示的是2014年来自台式机、手机和平板电脑的访问量，另一个条形图显示的是2015年的访问量。  

X轴上的类别是“桌面”、“手机”和“平板”。 Y轴表示X轴上每个类别的访问次数，所以Y轴是数值。  

您需要定义StackedBarChart使用的X轴和Y轴。 分类轴由JavaFX类JavaFX .scene.chart. categoryaxis表示。 数值轴由JavaFX类JavaFX .scene.chart. numberaxis表示。  

```java
CategoryAxis xAxis    = new CategoryAxis();
xAxis.setLabel("Devices");

NumberAxis yAxis = new NumberAxis();
yAxis.setLabel("Visits");
```

**在X轴上设置类型**

```java
CategoryAxis xAxis    = new CategoryAxis();
xAxis.setLabel("Devices");
xAxis.getCategories().addAll("Desktop", "Phone", "Tablet");
```

### 2. 创建一个StackedBarChart

```java
CategoryAxis xAxis    = new CategoryAxis();
xAxis.setLabel("Devices");
xAxis.getCategories().addAll("Desktop", "Phone", "Tablet");

NumberAxis yAxis = new NumberAxis();
yAxis.setLabel("Visits");

StackedBarChart stackedBarChart = new StackedBarChart(xAxis, yAxis);
```

### 3. 设置连续数据

```java
StackedBarChart     stackedBarChart = new StackedBarChart(xAxis, yAxis);

XYChart.Series dataSeries1 = new XYChart.Series();
dataSeries1.setName("Desktop");

dataSeries1.getData().add(new XYChart.Data("2014", 567));
dataSeries1.getData().add(new XYChart.Data("2015", 540));

stackedBarChart.getData().add(dataSeries1);

XYChart.Series dataSeries2 = new XYChart.Series();
dataSeries2.setName("Phone");

dataSeries2.getData().add(new XYChart.Data("2014"  , 65));
dataSeries2.getData().add(new XYChart.Data("2015"  , 120));

stackedBarChart.getData().add(dataSeries2);

XYChart.Series dataSeries3 = new XYChart.Series();
dataSeries3.setName("Tablet");

dataSeries3.getData().add(new XYChart.Data("2014"  , 23));
dataSeries3.getData().add(new XYChart.Data("2015"  , 36));

stackedBarChart.getData().add(dataSeries3);
```

### 4. 添加StackedBarChart 到Scene

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StackedBarChartExperiments extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StackedBarChart Experiments");

        CategoryAxis xAxis    = new CategoryAxis();
        xAxis.setLabel("Devices");
        xAxis.getCategories().addAll("Desktop", "Phone", "Tablet");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Visits");

        StackedBarChart     stackedBarChart = new StackedBarChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Desktop");

        dataSeries1.getData().add(new XYChart.Data("2014", 567));
        dataSeries1.getData().add(new XYChart.Data("2015", 540));

        stackedBarChart.getData().add(dataSeries1);

        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("Phone");

        dataSeries2.getData().add(new XYChart.Data("2014"  , 65));
        dataSeries2.getData().add(new XYChart.Data("2015"  , 120));

        stackedBarChart.getData().add(dataSeries2);

        XYChart.Series dataSeries3 = new XYChart.Series();
        dataSeries3.setName("Tablet");

        dataSeries3.getData().add(new XYChart.Data("2014"  , 23));
        dataSeries3.getData().add(new XYChart.Data("2015"  , 36));

        stackedBarChart.getData().add(dataSeries3);

        VBox vbox = new VBox(stackedBarChart);

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