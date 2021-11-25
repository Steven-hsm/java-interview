

* BeanFactory：Spring顶层核心接口，使用了简单工厂模式，负责生产Bean
* BeanDefinition：Spring顶层核心接口，封装了生成Bean的一切原料
* BeanDefinitionReader：
* BeanDefinitionRegister：
* BeanDefinitionSacnner：
* BeanFactoryPostProccessor: 修改Bean定义
* BeanDefinitionRegistryPostProcessor : Bean的注册
* BeanPostProcessor：bean后置处理器

Bean的加载过程：现将类加载成Bean定义，加载bean定义有以下步骤，读取配置类（BeanDefinitionReader）、扫描合格的bean（BeanDefinitionSacnner）、注册bean定义（BeanDefinitionRegister）。调用BeanFactory后置处理器（BeanFactoryPostProccessor）修改Bean定义。通过getBean从bean工厂

```java
public AnnotationConfigApplicationContext(Class<?>... componentClasses) {
    this();
    register(componentClasses);
    refresh();
}
```

```java
public AnnotationConfigApplicationContext() {
    //创建一个读取注解的bean定义读取器，完成了spring内部BeanDefinition的注册
	this.reader = new AnnotatedBeanDefinitionReader(this);
    //创建BeanDefinition扫描器，可以用来扫描包和类
	this.scanner = new ClassPathBeanDefinitionScanner(this);
}
```

FactoryBean：一个特殊的Bean，实现了这个接口，IOC调用实现类的getObject（）方法，动态实例化

