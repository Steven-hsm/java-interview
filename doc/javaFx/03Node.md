Node是所有展示在Scene组件的父类，Node是一个抽象类，你只能在Scene中增加它的子类

### 1. Node 概念

每个JavaFX Node(子类)实例只能添加到JavaFX Scene中一次。换句话说，每个Node实例只能出现在Scene中的一个位置。如果您多次尝试将相同的Node实例或Node子类实例添加到Scene中，JavaFX将抛出异常!

JavaFX节点有时可以有子项——也称为子节点。给定的Node实例是否可以有子节点取决于具体的Node子类。JavaFX Node的一个名为Parent的特殊子类用于对具有子节点的Node实例进行建模。因此，可以有子类的Node实例通常是Parent类的子类而不是直接的Node类的子类。

JavaFX Stage和JavaFX Scene类不是JavaFX Node类的子类。虽然这两个类用于显示在Scene中，但只有添加到Scene实例的Node实例才被认为是Scene的一部分。

一旦将Node实例附加到Scene中，就只允许JavaFX应用程序线程(管理Scene的线程)修改Node实例。

### 2. Node 属性

- A cartesian coordinate system：笛卡尔坐标系
- A bounding box delimited by:
  - Layout bounds
  - Bounds in local
  - Bounds in parent
- layoutX
- layoutY
- Preferred height：偏好高度
- Preferred width： 偏好宽度
- Minimum height： 最小高度
- Minimum width： 最小宽度
- Maximum height： 最大高度
- Maximum width：最小宽度
- User data：用户数据
- Items (Child nodes)： 子节点

每一个属性都会被子类覆盖

### 3. 属性详细说明

#### 3.1 坐标系

每个JavaFX节点都有自己的笛卡尔坐标系统。与常规笛卡尔坐标系的唯一区别是Y轴是相反的。这意味着，坐标系统的原点在坐标系统的左上角。随着Y值的增加，点从坐标系统的顶部向下移动。这种Y轴的反转在2D图形坐标系中是正常的。

![javafx-node-coordinate-system](C:\Users\Administrator\Desktop\images\javafx-node-coordinate-system.png)

JavaFX节点有可能具有负的X和Y坐标。

每个节点都有自己的坐标系统。这个坐标系统用于在父节点中定位子节点实例，或者在JavaFX画布上绘图时。这意味着，作为另一个Node的子节点的Node都有自己的坐标系统，并且在其父Node的坐标系统中有一个位置(X,Y)。

下面是一个父节点坐标系统的例子，其中子节点位于父节点坐标系统中的(25,25)。子节点也有自己的坐标系统，其中子节点位于父节点坐标系统中的(0,0)，即父节点坐标系统中的(25,25)。

![javafx-node-coordinate-system-2](C:\Users\Administrator\Desktop\images\javafx-node-coordinate-system-2.png)

#### 3.2 Node 边界

JavaFX节点有一个边界框。JavaFX节点的边界框是围绕节点形状的逻辑框。完整的节点位于边界框内——图形上是这样的。换句话说，节点的所有角和边都包含在边界框中，并且节点周围没有额外的空间，除非通过效果、填充或其他应用到节点上的东西添加。

| 名称           | 描述                                                        |
| -------------- | ----------------------------------------------------------- |
| layoutBounds   | 节点在其自身坐标空间中的边界-不应用任何效果、剪辑或转换。   |
| boundsInLocal  | 节点在其自己的坐标空间中的边界-应用效果和剪辑，但没有转换。 |
| boundsInParent | 节点在其父坐标空间中的边界-应用效果、剪辑和转换。           |

这些边界框的每个维度都可以从它们对应的具有相同名称的属性中读取，即名为layoutBounds、boundsInLocal和boundsInParent的属性。

父节点(parent)使用boundsInParent边界框来布局其子节点。父节点需要知道总空间，包括所有效果，剪辑和转换的节点，以便能够为它分配空间。

#### 3.3 layoutX and layoutY

向下，向右偏移量

```java
node.setLayoutX(100);
node.setLayoutY(200);
```

#### 3.4 偏好宽度和高度

```java
node.setPrefWidth(100);
node.setPrefHeight(100);
```

#### 3.5 最小宽度和高度

```java
node.setMinWidth(50);
node.setMinHeight(50);
```

#### 3.6 最大宽度和高度

```java
node.setMaxWidth(50);
node.setMaxHeight(50);
```

#### 3.7 用户数据

您可以使用setUserData()方法在JavaFX节点上设置用户数据。此方法接受您自己选择的任何Java对象。通过这种方式，您可以将业务对象附加到JavaFX Node实例。下面是一个将一些用户数据附加到Node实例的示例:

```java
node.setUserData(new MyObject("Hey - some data"));
```

#### 3.8 子节点

许多JavaFX Node子类可以包含项或子节点。具体如何添加和访问这些子节点取决于具体的Node子类。有些类有一个getItems()方法，返回项目的List。其他类有一个getChildren()方法做同样的事情。您必须检查具体的Node子类，以确定它是否可以有项或子节点，以及如何添加和访问它们。