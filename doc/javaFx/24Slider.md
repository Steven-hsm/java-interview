JavaFX Slider控件为用户提供了一种方法，通过将句柄滑动到表示所需值的所需点，从而在给定的时间间隔内选择值。JavaFX Slider由JavaFX类JavaFX .scene.control.Slider表示。下面是JavaFX滑块的截图:

### 1. 创建一个Slider

```java
Slider slider = new Slider(0, 100, 0);
```

### 2. 读取Slider值

```java
double value = slider.getValue();
```

### 3. 设置最大刻度单位

```java
slider.setMajorTickUnit(8.0);
```

### 4. 设置刻度最小数

```java
slider.setMajorTickUnit(8.0);
slider.setMinorTickCount(3);
```

### 5. 捕捉刻度

```java
slider.setSnapToTicks(true);
```

### 6. 显示刻度

```java
slider.setShowTickMarks(true);
```

### 7. 显示刻度值

```java
slider.setShowTickLabels(true);
```