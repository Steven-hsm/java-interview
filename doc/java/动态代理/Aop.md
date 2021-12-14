**AOP说明**

* 基于动态代理来实现。默认地，如果使用接口的，用 JDK 提供的动态代理实现，如果没有接口，使用 CGLIB 实现
* Spring 3.2 以后，spring-core 直接就把 CGLIB 和 ASM 的源码包括进来了
* Spring AOP 需要依赖于 IOC 容器来管理
* Spring AOP 只能作用于 Spring 容器中的 Bean，它是使用纯粹的 Java 代码实现的，只能作用于 bean 的方法
* Spring 提供了 AspectJ 的支持，但只用到的AspectJ的切点解析和匹配
* Spring AOP 是基于代理实现的，在容器启动的时候需要生成代理实例，在方法调用上也会增加栈的深度，使得 Spring AOP 的性能不如 AspectJ 那么好

**AspectJ说明**

* 属于静态织入，它是通过修改代码来实现的，它的织入时机可以是
  * Compile-time weaving：编译期织入，如类 A 使用 AspectJ 添加了一个属性，类 B 引 用了它，这个场景就需要编译期的时候就进行织入，否则没法编译类 B。
  * Post-compile weaving：编译后织入，也就是已经生成了 .class 文件，或已经打成 jar 包 了，这种情况我们需要增强处理的话，就要用到编译后织入
  * **Load-time weaving**：指的是在加载类的时候进行织入，要实现这个时期的织入
    * 1、自定义类加载器来干这个，这个应该是最容易想到的办法，在被织入类加 载到 JVM 前去对它进行加载，这样就可以在加载的时候定义行为了
    * 2、在 JVM 启动的时候 指定 AspectJ 提供的 agent：-javaagent:xxx/xxx/aspectjweaver.jar。
* AspectJ 能干很多 Spring AOP 干不了的事情，它是 **AOP 编程的完全解决方案**
* Spring AOP 致力于解决的 是企业级开发中最普遍的 AOP 需求（方法织入），而不是力求成为一个像 AspectJ 一样的 AOP 编程完全解决方 案。
* 因为 AspectJ 在实际代码运行前完成了织入，所以大家会说它生成的类是没有额外运行时开销的

### 1 Spring AOP

Spring AOP 是纯的 Spring 代码，和 AspectJ 没什么关系，但是 Spring 延用了 AspectJ 中的概念，包括使用了 AspectJ 提供的 jar 包中的注解，但是不依赖于其实现功能。 

Spring AOP 一共有三种配置方式,做到了很好地向下兼容

* Spring 1.2 **基于接口的配置**
* Spring 2.0 **schema-based 配置**
* Spring 2.0 **@AspectJ** **配置**

Advisor 决定该拦截哪些方法，拦截 后需要完成的工作还是内部的 Advice 来做