### 1. CGLib动态代理分析

 JDK实现动态代理需要实现类通过接口定义业务方法，对于没有接口的类，如何实现动态代理呢，这就需要CGLib了。CGLib采用了非常底层的字节码技术，其原理是通过字节码技术为一个类创建子类，并在子类中采用方法拦截的技术拦截所有父类方法的调用，顺势织入横切逻辑。JDK动态代理与CGLib动态代理均是实现Spring AOP的基础。

这里有两个比较重要的角色：

* 被代理类：真实的业务处理类
* 方法拦截器：用来拦截被代理类

### 2. 使用代码

```xml
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
    <version>3.2.5</version>
</dependency>
```

#### 2.1 被代理类

```java
public class UserDao     {
    public void save() {
        System.out.println("业务处理");
    }
}
```

#### 2.2 方法拦截类

```java
public class ProxyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object enhancerObj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("在调用之前，我要干点啥呢？");
        Object returnValue = methodProxy.invokeSuper(enhancerObj, args);
        System.out.println("在调用之后，我要干点啥呢？");
        return returnValue;
    }
}
```

#### 2.3 启动类

```java
public class Main {
    public static void main(String[] args) {
        //这里会保存生成之后的动态代理类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"D:\\home");
        //目标对象
        Enhancer en = new Enhancer();
        en.setSuperclass(UserDao.class);
        en.setCallback(new ProxyMethodInterceptor());
        UserDao userDao = (UserDao) en.create();
        userDao.save();
    }
}
```

可以看到，最终生成的类是![image-20211210193318484](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211210193318484.png)

#### 2.4 聊聊代理方法

```java
public Object intercept(Object enhancerObj,//这个是最终的增强类
                        Method method,//这个是调用的方法
                        Object[] args,//这个是调用的参数
                        MethodProxy methodProxy) throws Throwable {
}
```

基于intercept方法的参数，调用被代理方法的方式主要有两种

1. 直接全部使用方法上的参数

   ```shell
   methodProxy.invokeSuper(enhancerObj, args);
   ```

2. 外部传入被代理的类

   ```java
   public class ProxyFactory implements MethodInterceptor {
       //这里维护一个目标类，在调用拦截方法时使用目标类执行
   	private Object target;
       public ProxyFactory(Object target) {
           this.target = target;
       }
        @Override
       public Object intercept(Object enhancerObj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
           System.out.println("开启事务");
           // 执行目标对象的方法
           Object returnValue = method.invoke(target, args);
           System.out.println("关闭事务");
           return returnValue;
       }
   }
   ```

### 3. CGLib生成的代理类

JDK动态代理只能对实现了接口的类生成代理，而不能针对类 。CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法 。

因为是继承，所以该类或方法最好不要声明成final ，final可以阻止继承和多态。

#### 3.1 为什么是三个类？ 三个类分别什么作用？

​	Jdk动态代理的拦截对象是通过反射的机制来调用被拦截实例方法的，反射的效率比较低，所以cglib采用了FastClass的机制来实现对被拦截方法的调用。FastClass机制就是对一个类的方法建立索引，调用方法时根据方法的签名来计算索引，通过索引来直接调用相应的方法。

CGLIB生成的动态代理会包含3个class文件，一个是生成的代理类，另外两个类都是FastClass机制需要的。一个class为生成的代理类中的每个方法建立了索引，另外一个则为我们被代理类的所有方法包含其父类的方法建立了索引。

#### 3.2 主要看看生成的代理类

```java
public class UserDao$$EnhancerByCGLIB$$8a1a4f11 extends UserDao implements Factory {
    //代理类的防范
	public final void save() {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        if (var10000 != null) {
            var10000.intercept(this, CGLIB$save$0$Method, CGLIB$emptyArgs, CGLIB$save$0$Proxy);
        } else {
            super.save();
        }
    }
}
```

想深入的可以自己深入调试并看看最终三成的三个类