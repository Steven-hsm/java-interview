### 1. 将列表list转换为Map

#### 1.1 没有重复数据

```java
dataList.stream()
        .collect(Collectors.toMap(Object::getFunction, Function.identity()); //存在重复数据会报错
```

#### 1.2 存在重复数据特殊处理

```java
dataList.stream()
        .collect(Collectors.toMap(Object::getFunction, Function.identity(),
                                  (v1, v2) -> v1)); //这里特殊处理，存在重复数据，返回第一个
```

### 2. 列表针对某个字段求和

```java
dataList.stream().mapToInt(Object::getFunction).sum();
```

