### 1. JDK动态代理分析

代理是一种常用的设计模式，其目的就是为其他对象提供一个代理以控制对某个对象的访问。代理类负责为委托类预处理消息，过滤消息并转发消息，以及进行消息被委托类执行后的后续处理。

就jdk动态代理而言，代理的UML图如下

![img](https://img-blog.csdn.net/20160807170246461?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

这里又三个重要的角色

* 接口：代理类和被代理类
* 被代理类：真实业务处理的类
* 代理类：由jdk动态代理生成的类，一般以$Prxoy0结尾

### 2. 使用代码

#### 2.1 接口类

```java
public interface InterfaceObject {
    void sayhello();
}
```

#### 2.2被代理类

```java
public class RealObject implements InterfaceObject{
    @Override
    public void sayhello() {
        System.out.println("say hello");
    }
}
```

#### 2.3 调用处理器

调用处理器优点类似于spring的aop功能，spring的aop也是基于动态代理实现的。

```java
public class ObjectInvocationHandler implements InvocationHandler {
    private Object subject;

    public ObjectInvocationHandler(Object subject) {
        this.subject = subject;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("在调用之前，我要干点啥呢？");
        Object returnValue = method.invoke(subject, args);
        System.out.println("在调用之后，我要干点啥呢？");
        return returnValue;
    }
}
```

#### 2.4 运行方法

```java
public class Main {
    public static void main(String[] args) {
        //这里基于1.8，用于保存jdk动态生成的代理类
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        RealObject realObject = new RealObject();
        ObjectInvocationHandler objectInvocationHandler = new ObjectInvocationHandler(realObject);

        ClassLoader loader = realObject.getClass().getClassLoader();
        Class[] interfaces = realObject.getClass().getInterfaces();
        //这里的功能其实很明显，就是为了获取最终的代理类，那么具体的逻辑就可以去看看代理类的执行逻辑了
        InterfaceObject interfaceObject = (InterfaceObject) Proxy.newProxyInstance(loader, interfaces, objectInvocationHandler);
        interfaceObject.sayhello();
    }
}
```

在运行时打断点查看返回的 interfaceObject的具体实现，可以看到这个类是

![image-20211210174642081](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211210174642081.png)

#### 3.源码分析

1. 调用代理方法获取最终的代理类

   ```java
   InterfaceObject interfaceObject = (InterfaceObject) Proxy.newProxyInstance(loader, interfaces, objectInvocationHandler);
   ```
   
   这句代码很关键，有兴趣的可以跟进去看一下，这里的主要目的是生成代理类
   
2. 代理类分析（这里贴主要代码）

   ```java
   //代理类实现了InterfaceObject
   public final class $Proxy0 extends Proxy implements InterfaceObject {
       //实现了接口的业务方法sayhello，最终运行调用的就是这个方法
   	public final void sayhello() throws  {
           try {
   		   //这里最终调用的其实就是我们的处理器方法
               super.h.invoke(this, m3, (Object[])null);
           } catch (RuntimeException | Error var2) {
               throw var2;
           } catch (Throwable var3) {
               throw new UndeclaredThrowableException(var3);
           }
       }
       //这里可以看到m3就是我们的sayhello方法
       static {
           try {
               m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
               m2 = Class.forName("java.lang.Object").getMethod("toString");
               m3 = Class.forName("com.hsm.java.proxy.jdk.InterfaceObject").getMethod("sayhello");
               m0 = Class.forName("java.lang.Object").getMethod("hashCode");
           } catch (NoSuchMethodException var2) {
               throw new NoSuchMethodError(var2.getMessage());
           } catch (ClassNotFoundException var3) {
               throw new NoClassDefFoundError(var3.getMessage());
           }
       }
   }
   ```
   
   

