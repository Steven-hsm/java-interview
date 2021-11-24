### 1.1. Spring IoC容器和bean的介绍

它们所使用的对象，是通过构造函数参数，工厂方法的参数或这是从工厂方法的构造函数或返回值的对象实例设置的属性，然后容器在创建bean时注入这些需要的依赖

bean本身通过直接构造类来控制依赖关系的实例化或位置，或提供诸如服务定位器模式之类的机制。

[`BeanFactory`](https://docs.spring.io/spring-framework/docs/5.1.3.BUILD-SNAPSHOT/javadoc-api/org/springframework/beans/factory/BeanFactory.html) 接口提供了一种更先进的配置机制来管理任意类型的对象. [`ApplicationContext`](https://docs.spring.io/spring-framework/docs/5.1.3.BUILD-SNAPSHOT/javadoc-api/org/springframework/context/ApplicationContext.html) 是`BeanFactory`的子接口. 他提供了:

- 更容易与Spring的AOP特性集成
- 消息资源处理(用于国际化)
- 事件发布
- 应用层特定的上下文，如用于web应用程序的`WebApplicationContext`

`BeanFactory`提供了配置框架的基本功能，`ApplicationContext`添加了更多特定于企业的功能。 `ApplicationContext`完全扩展了`BeanFactory`的功能

### 1.2. 容器概述

`ApplicationContext`是Spring IoC容器实现的代表，它负责实例化，配置和组装Bean、

容器通过读取配置元数据获取有关实例化、配置和组装哪些对象的说明 。配置元数据可以使用**XML**、**Java注解**或**Java代码**来呈现

#### 1.2.1. 配置元数据

配置元数据表示了应用开发人员告诉Spring容器以何种方式实例化、配置和组装应用程序中的对象

配置元数据通常以简单、直观的XML格式提供.XML并不是配置元数据的唯一方式，Spring IoC容器本身是完全与元数据配置的实际格式分离的。现在，许多开发人员选择[基于Java的配置](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-java)来开发应用程序。

* 基于XML格式：最早的方式
* [基于注解的配置](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-annotation-config): Spring 2.5 支持基于注解的元数据配置.
* [基于Java的配置](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-java): 从 Spring 3.0开始, 由Spring JavaConfig项目提供的功能已经成为Spring核心框架的一部分

#### 1.2.2. 实例化容器

提供给ApplicationContext构造函数的路径就是实际的资源字符串，使容器能从各种外部资源(如本地文件系统、Java类路径等)装载元数据配置

```java
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
```

#### 1.2.3. 使用容器

`ApplicationContext`是能够创建bean定义以及处理相互依赖关系的高级工厂接口，使用方法`T getBean(String name, Class<T> requiredType)`获取容器实例。

### 1.3. Bean 的概述

Spring IoC容器管理一个或多个bean。这些bean是由您提供给容器的元数据配置创建的(例如，XML `<bean/>`定义的形式)。

在容器内部，这些bean定义表示为`BeanDefinition`对象，其中包含（其他信息）以下元数据:

* 限定包类名称: 通常，定义的bean的实际实现类。
* bean行为配置元素, 定义Bean的行为约束(例如作用域，生命周期回调等等）
* bean需要引用其他bean来完成工作. 这些引用也称为协作或依赖关系.
* 其他配置用于新对象的创建，例如使用bean的数量来管理连接池，或者限制池的大小。

除了bean定义包含如何创建特定的bean的信息外，`ApplicationContext`实现还允许用户在容器中注册现有的、已创建的对象.通过`getBeanFactory()`访问到返回值为`DefaultListableBeanFactory`的实现的ApplicationContext的BeanFactory `DefaultListableBeanFactory`支持通过`registerSingleton(..)`和`registerBeanDefinition(..)`方法来注册对象

#### 1.3.1. 命名bean

每个bean都有一个或多个标识符，这些标识符在容器托管时必须是唯一的。bean通常只有一个标识符，但如果需要到的标识不止一个时，可以考虑使用别名。在基于XML的配置中，开发者可以使用`id`属性，name属性或两者都指定bean的标识符

#### 1.3.2. 实例化Bean

bean定义基本上就是用来创建一个或多个对象的配置，当需要bean的时候，容器会查找配置并且根据bean定义封装的元数据来创建（或获取）实际对象。

class属性实际上就是`BeanDefinition`实例中的`class`属性,他通常是必需的,有两种方式使用Class属性:

* 通常情况下，会直接通过反射调用构造方法来创建bean，这种方式与Java代码的`new`创建相似
* 通过静态工厂方法创建，类中包含静态方法。通过调用静态方法返回对象的类型可能和Class一样，也可能完全不一样

##### 使用构造器实例化

```java
<bean id="exampleBean" class="examples.ExampleBean"/>
```

##### 使用静态工厂方法实例化

```java
<bean id="clientService"
    class="examples.ClientService"
    factory-method="createInstance"/>
```

##### 使用实例工厂方法实例化

```java
<!-- the factory bean, which contains a method called createInstance() -->
<bean id="serviceLocator" class="examples.DefaultServiceLocator">
    <!-- inject any dependencies required by this locator bean -->
</bean>

<!-- the bean to be created via the factory bean -->
<bean id="clientService"
    factory-bean="serviceLocator"
    factory-method="createClientServiceInstance"/>
```

### 1.4. 依赖

一般情况下企业应用不会只有一个对象（Spring Bean），甚至最简单的应用都需要多个对象协同工作

#### 1.4.1. 依赖注入

依赖注入是让对象只通过构造参数、工厂方法的参数或者配置的属性来定义他们的依赖的过程。这些依赖也是其他对象所需要协同工作的对象， 容器会在创建Bean的时候注入这些依赖。整个过程完全反转了由Bean自己控制实例化或者依赖引用，所以这个过程也称之为“控制反转”

##### 基于构造函数的依赖注入

##### 基于setter方法的依赖注入

容器解析Bean的过程如下:

* 创建并根据描述的元数据来实例化`ApplicationContext`，元数据配置可以是XML文件、Java代码或者注解。
* 每一个Bean的依赖都通过构造函数参数或属性，或者静态工厂方法的参数等等来表示。这些依赖会在Bean创建的时候装载和注入
* 每一个属性或者构造函数的参数都是真实定义的值或者引用容器其他的Bean.
* 每一个属性或者构造参数可以根据指定的类型转换为所需的类型。Spring也可以将String转成默认的Java内置类型。例如`int`, `long`, `String`, `boolean`等 。

**循环依赖**

如果开发者主要使用基于构造函数的依赖注入，那么很有可能出现循环依赖的情况。

#### 1.4.2. 依赖和配置的细节

您可以将bean的属性和构造函数参数定义为对其他bean的引用，或者作为其内联定义的值

#### 1.4.3. 使用 `depends-on`属性

`depends-on`属性既可以指定初始化时间依赖性，也可以指定单独的bean，相应的销毁时间的依赖。独立定义了`depends-on`属性的bean会优先销毁 （相对于`depends-on`的bean销毁，这样`depends-on`可以控制销毁的顺序。

#### 1.4.4. 懒加载Bean

默认情况下， `ApplicationContext` 会在实例化的过程中创建和配置所有的单例[singleton](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-scopes-singleton) bean。开发者可以通过将Bean标记为延迟加载就能阻止这个预初始化

#### 1.4.5. 自动装配

* 自动装载能够明显的减少指定的属性或者是构造参数
* 自动装载可以扩展开发者的对象

装配模式：

* no：不自动装配
* byName：按属性名称自动装配
* byType：如果需要自动装配的属性的类型在容器中只存在一个的话，他允许自动装配。如果存在多个，则抛出致命异常
* constructor：类似于`byType`，但应用于构造函数参数。 如果容器中没有一个Bean的类型和构造函数参数类型一致的话，则会引发致命错误。

#### 1.4.6.方法注入

### 1.5. bean的作用域

* [singleton](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-scopes-singleton)：(默认) 每一Spring IOC容器都拥有唯一的实例对象。
* [prototype](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-scopes-prototype)：一个Bean定义可以创建任意多个实例对象.
* [request](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-scopes-request)：将单个bean定义范围限定为单个HTTP请求的生命周期。 也就是说，每个HTTP请求都有自己的bean实例，它是在单个bean定义的后面创建的。 只有基于Web的Spring `ApplicationContext`的才可用。
* [session](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-scopes-session)：将单个bean定义范围限定为HTTP `Session`的生命周期。 只有基于Web的Spring `ApplicationContext`的才可用。
* [application](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-scopes-application)：将单个bean定义范围限定为`ServletContext`的生命周期。 只有基于Web的Spring `ApplicationContext`的才可用。
* [websocket](https://github.com/DocsHome/spring-docs/blob/master/pages/web/web.md#websocket-stomp-websocket-scope)：将单个bean定义范围限定为 `WebSocket`的生命周期。 只有基于Web的Spring `ApplicationContext`的才可用。

### 1.6. 自定义Bean的特性

#### 1.6.1. 生命周期回调

你可以实现`InitializingBean` 和 `DisposableBean`接口，让容器里管理Bean的生命周期。容器会在调用`afterPropertiesSet()` 之后和`destroy()`之前会允许bean在初始化和销毁bean时执行某些操作。

#### 1.6.2. `ApplicationContextAware` 和 `BeanNameAware`

当`ApplicationContext` 创建实现`org.springframework.context.ApplicationContextAware`接口的对象实例时，将为该实例提供对该 `ApplicationContext`.的引用

```java
public interface ApplicationContextAware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
```

当`ApplicationContext`创建实现了`org.springframework.beans.factory.BeanNameAware`接口的类，那么这个类就可以针对其名字进行配置。以下清单显示了BeanNameAware接口的定义

```java
public interface BeanNameAware {
    void setBeanName(String name) throws BeansException;
}
```

#### 1.6.3. 其他的 `Aware`接口

除了 `ApplicationContextAware`和`BeanNameAware`（前面已讨论过）之外，Spring还提供了一系列`Aware`接口，让bean告诉容器，它们需要一些具体的基础配置信息。。 一些重要的`Aware`接口参看下表：

| 名称                             | 注入的依赖                                                   | 所对应的章节                                                 |
| -------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `ApplicationContextAware`        | 声明 `ApplicationContext`                                    | [`ApplicationContextAware` 和 `BeanNameAware`](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-aware) |
| `ApplicationEventPublisherAware` | `ApplicationContext`的事件发布者                             | [`ApplicationContext`的其他功能](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#context-introduction) |
| `BeanClassLoaderAware`           | 用于加载bean类的类加载器                                     | [实例化Bean](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-class) |
| `BeanFactoryAware`               | 声明 `BeanFactory`.                                          | [`ApplicationContextAware` 和 `BeanNameAware`](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-aware) |
| `BeanNameAware`                  | 声明bean的名称.                                              | [`ApplicationContextAware` 和 `BeanNameAware`](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-aware) |
| `BootstrapContextAware`          | 容器运行的资源适配器`BootstrapContext`。通常仅在JCA感知的 `ApplicationContext` 实例中可用 | [JCA CCI](https://github.com/DocsHome/spring-docs/blob/master/pages/integration/integration.md#cci) |
| `LoadTimeWeaverAware`            | 定义的weaver用于在加载时处理类定义.                          | [在Spring框架中使用AspectJ进行加载时织入](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#aop-aj-ltw) |
| `MessageSourceAware`             | 用于解析消息的已配置策略（支持参数化和国际化）               | [`ApplicationContext`的其他作用](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#context-introduction) |
| `NotificationPublisherAware`     | Spring JMX通知发布者                                         | [通知](https://github.com/DocsHome/spring-docs/blob/master/pages/integration/integration.md#jmx-notifications) |
| `ResourceLoaderAware`            | 配置的资源加载器                                             | [资源](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#resources) |
| `ServletConfigAware`             | 当前`ServletConfig`容器运行。仅在Web下的Spring `ApplicationContext`中有效 | [Spring MVC](https://github.com/DocsHome/spring-docs/blob/master/pages/web/web.md#mvc) |
| `ServletContextAware`            | 容器运行的当前ServletContext。仅在Web下的Spring `ApplicationContext`中有效。 | [Spring MVC](https://github.com/DocsHome/spring-docs/blob/master/pages/web/web.md#mvc) |

### 1.7. bean定义的继承

bean定义可以包含许多配置信息，包括构造函数参数，属性值和特定于容器的信息，

子bean如果没有指定class，它将使用父bean定义的class。但也可以进行重写。在后一种情况中，子bean必须与父bean兼容，也就是说，它必须接受父bean的属性值。

子bean定义从父类继承作用域、构造器参数、属性值和可重写的方法，除此之外，还可以增加新值。开发者指定任何作用域、初始化方法、销毁方法和/或者静态工厂方法设置都会覆盖相应的父bean设置。

### 1.8. 容器的扩展点

应用程序开发者无需继承`ApplicationContext`的实现类。相反，Spring IoC容器可以通过插入特殊的集成接口实现进行扩展

#### 1.8.1. 使用`BeanPostProcessor`自定义Bean

`BeanPostProcessor`接口定义了可以实现的回调方法，以提供您自己的（或覆盖容器的默认）实例化逻辑，依赖关系解析逻辑等。如果要在Spring容器完成实例化，配置和初始化bean之后实现某些自定义逻辑，则可以插入一个或多个`BeanPostProcessor`实现。

您可以配置多个`BeanPostProcessor` 实例，并且可以通过设置`order`属性来控制这些 `BeanPostProcessor` 实例的执行顺序。

`BeanPostProcessor`实例在bean（或对象）实例上运行。 也就是说，Spring IoC容器实例化一个bean实例，然后才能用`BeanPostProcessor` 对这个实例进行处理。

`BeanPostProcessor`会在整个容器内起作用，所有它仅仅与正在使用的容器相关。如果在一个容器中定义了`BeanPostProcessor`，那么它只会处理那个容器中的bean。 换句话说，在一个容器中定义的bean不会被另一个容器定义的`BeanPostProcessor`处理，即使这两个容器都是同一层次结构的一部分。

要更改实际的bean定义（即定义bean的蓝图），您需要使用`BeanFactoryPostProcessor`，使用BeanFactoryPostProcessor自定义配置元数据。

`ApplicationContext`会自动地检测所有定义在配置元文件中，并实现了`BeanPostProcessor` 接口的bean。`ApplicationContext`会注册这些beans为后置处理器， 使他们可以在bean创建完成之后被调用。bean后置处理器可以像其他bean一样部署到容器中。

#### 1.8.2. 使用`BeanFactoryPostProcessor`自定义元数据配置

`BeanFactoryPostProcessor`操作bean的元数据配置，Spring IoC容器允许`BeanFactoryPostProcessor`读取配置元数据， 并可能在容器实例化除`BeanFactoryPostProcessor`实例之外的任何bean之前更改它

您可以配置多个`BeanFactoryPostProcessor`实例，并且可以通过设置`order`属性来控制这些`BeanFactoryPostProcessor`实例的运行顺序（`BeanFactoryPostProcessor`必须实现了`Ordered`接口才能设置这个属性）

`BeanFactoryPostProcessor`会在整个容器内起作用，所有它仅仅与正在使用的容器相关。如果在一个容器中定义了`BeanFactoryPostProcessor`， 那么它只会处理那个容器中的bean。 换句话说，在一个容器中定义的bean不会被另一个容器定义的`BeanFactoryPostProcessor`处理，即使这两个容器都是同一层次结构的一部分。

`ApplicationContext` 自动检测部署到其中的任何实现`BeanFactoryPostProcessor`接口的bean。 它在适当的时候使用这些bean作为bean工厂后置处理器。 你可以部署这些后置处理器为你想用的任意其它bean。

#### 1.8.3. 使用`FactoryBean`自定义初始化逻辑

`FactoryBean`接口就是Spring IoC容器实例化逻辑的可插拔点，如果你的初始化代码非常复杂，那么相对于（潜在地）大量详细的XML而言，最好是使用Java语言来表达。 你可以创建自定义的`FactoryBean`，在该类中编写复杂的初始化代码。然后将自定义的`FactoryBean`插入到容器中。

`FactoryBean`接口提供下面三个方法：

* `Object getObject()`: 返回这个工厂创建的对象实例。这个实例可能是共享的，这取决于这个工厂返回的是单例还是原型实例。
* `boolean isSingleton()`: 如果`FactoryBean`返回单例，那么这个方法就返回`true`，否则返回`false`。
* `Class getObjectType()`: 返回由`getObject()`方法返回的对象类型，如果事先不知道的类型则会返回null。

### 1.9. 基于注解的容器配置

注解注入在XML注入之前执行，因此同时使用这两种方式进行注入时，XML配置会覆盖注解配置。

#### 1.9.1. @Required

`@Required`注解适用于bean属性setter方法，此注解仅表示受影响的bean属性必须在配置时通过bean定义中的显式赋值或自动注入值。如果受影响的bean属性尚未指定值，容器将抛出异常；这导致及时的、明确的失败，避免在运行后再抛出`NullPointerException`或类似的异常

```java
public class SimpleMovieLister {

    private MovieFinder movieFinder;

    @Required
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // ...
}
```

#### 1.9.2. `@Autowired`

可以使用JSR 330的 `@Inject`注解代替本节中包含的示例中的Spring的`@Autowired`注解。

#### 1.9.3. `@Primary`

由于按类型的自动注入可能匹配到多个候选者，所以通常需要对选择过程添加更多的约束

表示如果存在多个候选者且另一个bean只需要一个特定类型的bean依赖时，就明确使用标记有`@Primary`注解的那个依赖

#### 1.9.4. 使用qualifiers微调基于注解的自动装配

`@Primary` 是一种用于解决自动装配多个值的注入的有效的方法，当需要对选择过程做更多的约束时，可以使用Spring的`@Qualifier`注解，可以为指定的参数绑定限定的值。 缩小类型匹配集，以便为每个参数选择特定的bean

#### 1.9.5. 使用泛型作为自动装配限定符

除了`@Qualifier` 注解之外，您还可以使用Java泛型类型作为隐式的限定形式

#### 1.9.6. `CustomAutowireConfigurer`

[`CustomAutowireConfigurer`](https://docs.spring.io/spring-framework/docs/5.1.3.BUILD-SNAPSHOT/javadoc-api/org/springframework/beans/factory/annotation/CustomAutowireConfigurer.html) 是一个`BeanFactoryPostProcessor`，它允许开发者注册自定义的qualifier注解类型，而无需指定`@Qualifier`注解`AutowireCandidateResolver` 通过以下方式确定自动注入的候选者:

- 每个bean定义的`autowire-candidate`值
- 在`<beans/>`元素上使用任何可用的 `default-autowire-candidates` 模式
- 存在 `@Qualifier` 注解以及使用`CustomAutowireConfigurer`注册的任何自定义注解

当多个bean有资格作为自动注入的候选项时，“primary”的确定如下：如果候选者中只有一个bean定义的 `primary`属性设置为`true`，则选择它。

#### 1.9.7. `@Resource`

Spring还通过在字段或bean属性setter方法上使用JSR-250 `@Resource`注解来支持注入。

`@Resource` 接受一个name属性.。默认情况下，Spring将该值解释为要注入的bean名称。 换句话说，它遵循按名称语义

#### 1.9.8. `@PostConstruct` 和 `@PreDestroy`

替代[初始化回调](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-lifecycle-initializingbean)和[销毁回调](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-lifecycle-disposablebean)

### 1.10. 类路径扫描和管理组件

#### 1.10.1. `@Component`注解和更多模板注解

#### 1.10.2. 使用元注解和组合注解

#### 1.10.3. 自动探测类并注册bean定义

#### 1.10.4. 在自定义扫描中使用过滤器

默认情况下，使用`@Component`, `@Repository`, `@Service`,`@Controller`注解的类或者注解为`@Component`的自定义注解类才能被检测为候选组件。 但是，开发者可以通过应用自定义过滤器来修改和扩展此行为。将它们添加为`@ComponentScan`注解的`includeFilters`或`excludeFilters`参数(或作为`component-scan` 元素

#### 1.10.5.在组件中定义bean的元数据

#### 1.10.6. 命名自动注册组件

#### 1.10.8. 为注解提供Qualifier元数据

#### 1.10.9. 生成候选组件的索引

### 1.11. 使用JSR 330标准注解

#### 1.11.1. 使用`@Inject` 和 `@Named`注解实现依赖注入

#### 1.11.2. `@Named` 和 `@ManagedBean`注解: 标准与 `@Component` 注解相同

#### 1.11.3. 使用 JSR-330标准注解的限制

| Spring              | javax.inject.*        | javax.inject restrictions / comments                         |
| ------------------- | --------------------- | ------------------------------------------------------------ |
| @Autowired          | @Inject               | `@Inject` 没有'required'属性。 可以与Java 8的 `Optional`一起使用。 |
| @Component          | @Named / @ManagedBean | JSR-330不提供可组合模型，只是一种识别命名组件的方法。        |
| @Scope("singleton") | @Singleton            | JSR-330的默认作用域就像Spring的`prototype`。 但是，为了使其与Spring的一般默认值保持一致，默认情况下，Spring容器中声明的JSR-330 bean是一个 `singleton`。 为了使用除 `singleton`之外的范围，您应该使用Spring的`@Scope`注解。 `javax.inject`还提供了[@Scope](https://download.oracle.com/javaee/6/api/javax/inject/Scope.html)注解。 然而，这个仅用于创建自己的注解。 |
| @Qualifier          | @Qualifier / @Named   | `javax.inject.Qualifier` 只是用于构建自定义限定符的元注解。 可以通过`javax.inject.Named`创建与Spring中`@Qualifier`一样的限定符。 |
| @Value              | -                     | 无                                                           |
| @Required           | -                     | 无                                                           |
| @Lazy               | -                     | 无                                                           |
| ObjectFactory       | Provider              | `javax.inject.Provider` avax.inject.Provider是Spring的`ObjectFactory`的直接替代品， 仅仅使用简短的`get()`方法即可。 它也可以与Spring的`@Autowired`结合使用，也可以与非注解的构造函数和setter方法结合使用。 |

### 1.12. 基于Java的容器配置

#### 1.12.1. 基本概念: `@Bean` 和 `@Configuration`

`@Bean`注解用于表明方法的实例化，、配置和初始化都是由Spring IoC容器管理的新对象，对于那些熟悉Spring的`<beans/>`XML配置的人来说， `@Bean`注解扮演的角色与`<beans/>`元素相同。开发者可以在任意的Spring `@Component`中使用`@Bean`注解方法 ，但大多数情况下，`@Bean`是配合`@Configuration`使用的。

使用`@Configuration`注解类时，这个类的目的就是作为bean定义的地方。此外，`@Configuration`类允许通过调用同一个类中的其他`@Bean`方法来定义bean间依赖关系

#### 1.12.2. 使用`AnnotationConfigApplicationContext`初始化Spring容器

一个强大的(versatile)`ApplicationContext` 实现,它不仅能解析`@Configuration`注解类 ,也能解析 `@Component`注解的类和使用JSR-330注解的类.
当使用`@Configuration`类作为输入时,`@Configuration`类本身被注册为一个bean定义,类中所有声明的`@Bean`方法也被注册为bean定义.当提供 `@Component`和JSR-330类时，它们被注册为bean定义，并且假定在必要时在这些类中使用DI元数据，例如`@Autowired` 或 `@Inject`。
#### 1.12.3. 使用`@Bean` 注解

@Bean是一个方法级别的注解，它与XML中的 `<bean/>`元素类似。注解支持 `<bean/>`提供的一些属性，例如 * [init-method](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-lifecycle-initializingbean) * [destroy-method](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-lifecycle-disposablebean) * [autowiring](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-factory-autowire) * `name`开发者可以在`@Configuration`类或`@Component`类中使用`@Bean`注解。

#### 1.12.4. 使用 `@Configuration` 注解

`@Configuration`是一个类级别的注解,表明该类将作为bean定义的元数据配置. `@Configuration`类会将有`@Bean`注解的公开方法声明为bean, .在 `@Configuration`类上调用`@Bean`方法也可以用于定义bean间依赖关系

#### 1.12.5. 编写基于Java的配置

##### 使用`@Import` 注解

**在导入的`@Bean`定义上注入依赖项**

### 1.13. 抽象环境

[`Environment`](https://docs.spring.io/spring-framework/docs/5.1.3.BUILD-SNAPSHOT/javadoc-api/org/springframework/core/env/Environment.html)接口是集成在容器中的抽象，它模拟了应用程序环境的两个关键方面：[profiles](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-definition-profiles) 和 [properties](https://github.com/DocsHome/spring-docs/blob/master/pages/core/IoC-container.md#beans-property-source-abstraction)。

#### 1.13.1. Bean定义Profiles

bean定义profiles是核心容器内的一种机制，该机制能在不同环境中注册不同的bean。

##### 使用 `@Profile`

[`@Profile`](https://docs.spring.io/spring-framework/docs/5.1.3.BUILD-SNAPSHOT/javadoc-api/org/springframework/context/annotation/Profile.html)注解用于当一个或多个配置文件激活的时候,用来指定组件是否有资格注册

#### 1.13.2. `PropertySource` 抽象

Spring的`Environment`抽象提供用于一系列的propertysources属性配置文件的搜索操作.

#### 1.13.3. 使用 `@PropertySource`

[`@PropertySource`](https://docs.spring.io/spring-framework/docs/5.1.3.BUILD-SNAPSHOT/javadoc-api/org/springframework/context/annotation/PropertySource.html) 注解提供了便捷的方式，用于增加`PropertySource`到Spring的 `Environment`中。

#### 1.13.4. 在声明中的占位符

之前，元素中占位符的值只能针对JVM系统属性或环境变量进行解析。现在已经打破了这种情况。因为环境抽象集成在整个容器中，所以很容易通过它来对占位符进行解析. 这意味着开发者可以以任何喜欢的方式来配置这个解析过程，可以改变是优先查找系统properties或者是有限查找环境变量，或者删除它们；增加自定义property源，使之成为更合适的配置

### 1.14. 注册`LoadTimeWeaver`

`LoadTimeWeaver`被Spring用来在将类加载到Java虚拟机(JVM)中时动态地转换类

### 1.15.`ApplicationContext`的附加功能

#### 1.15.1. 使用`MessageSource`实现国际化

#### 1.15.2. 标准和自定义事件

#### 1.15.3.通过便捷的方式访问底层资源

#### 1.15.4. 快速对Web应用的ApplicationContext实例化

#### 1.15.5. 使用Java EE RAR文件部署Spring的`ApplicationContext`

### 1.16.`BeanFactory`

`BeanFactory` API为Spring的IoC功能提供了基础。 它的特定契约主要用于与Spring的其他部分和相关的第三方框架集成其

`DefaultListableBeanFactory`实现是更高级别`GenericApplicationContext`容器中的密钥委托。

`BeanFactory`和相关接口（例如`BeanFactoryAware`, `InitializingBean`，`DisposableBean`）是其他框架组件的重要集成点。 通过不需要任何注解或甚至反射，它们允许容器与其组件之间的非常有效的交互。 应用程序级bean可以使用相同的回调接口，但通常更喜欢通过注解或通过编程配置进行声明性依赖注入。

#### 1.16.1. 选择`BeanFactory`还是`ApplicationContext`?

您应该使用`ApplicationContext`，除非您有充分的理由不这样做，使用`GenericApplicationContext`及其子类`AnnotationConfigApplicationContext`作为自定义引导的常见实现。 这些是Spring用于所有常见目的的核心容器的主要入口点：加载配置文件，触发类路径扫描，以编程方式注册bean定义和带注解的类，以及（从5.0开始）注册功能bean定义。

因为`ApplicationContext`包括`BeanFactory`的所有功能，和`BeanFactory`相比更值得推荐，除了一些特定的场景，例如在资源受限的设备上运行的内嵌的应用。 在`ApplicationContext`（例如`GenericApplicationContext`实现）中，按照约定（即通过bean名称或bean类型 - 特别是后处理器）检测到几种bean， 而普通的`DefaultListableBeanFactory`对任何特殊bean都是不可知的。
