### 1. 类加载运行全过程

当我们用java命令运行某个类的main函数启动程序时，首先需要通过**类加载器**把主类加载到 JVM。

Window系统下：

1. java.exe调用底层的jvm.dll文件创建java虚拟机（C++）
2. 创建一个引导类加载器实例(C++)
3. C++调用java代码创建JVM启动器实例sun.misc.Launcher,该类由引导类加载器负责加载，创建其他类加载器
4. 获取运行类自己的加载器ClassLoader，一般是AppClassLoader
5. 调用loadClass加载要运行的类
6. 加载完成之后JVM执行运行类的main方法入口

**loadClass**的类加载过程：**加载 >> 验证 >> 准备 >> 解析 >> 初始化 >>** 使用 >> 卸载 

* 加载：在硬盘上查找并通过IO读入字节码文件，使用到类时才会加载
  * 调用类的 main()方法，new对象等等，在加载阶段会在内存中生成一个**代表这个类的java.lang.Class对象**作为方法区这个类的各种数据的访问入口
* 验证：校验字节码文件的正确性
* 准备：给类的静态变量分配内存，并赋予默认值 
* 解析：将**符号引用**替换为直接引用
  * **静态链接**(类加载期间完成)：把一些静态方法(符号引用，比如 main()方法)替换为指向数据所存内存的指针或句柄等(直接引用)】
  * **动态链接**：程序运行期间完成的将符号引用替换为直接引用
* **初始化**：对类的静态变量初始化为指定的值，执行静态代码块

类被加载到方法区中后主要包含 **运行时常量池、类型信息、字段信息、方法信息、类加载器的引用、对应class实例的引用**等信息

**类加载器的引用**：这个类到类加载器实例的引用 

**对应class实例的引用**：类加载器在加载类信息放到方法区中后，会创建一个对应的Class 类型的 对象实例放到堆(Heap)中, 作为开发人员访问方法区中类定义的入口和切入点。 

> 主类在运行过程中如果使用到其它类，会逐步加载这些类。jar包或war包里的类不是一次性全部加载的，是使用到时才加载。 

### 2. 类加载器和双亲委派机制

Java里有如下几种类加载器:

* 引导类加载器：负责加载支撑JVM运行的位于JRE的lib目录下的核心类库，比如 rt.jar、charsets.jar等 
* 扩展类加载器：负责加载支撑JVM运行的位于JRE的lib目录下的ext扩展目录中的JAR 类包
* 应用程序类加载器：负责加载ClassPath路径下的类包，主要就是加载你自己写的那 些类
* 自定义加载器：负责加载用户自定义路径下的类包 

```java
public class TestJDKClassloader {
    public static void main(String[] args) {
        System.out.println("String类的加载器:" + String.class.getClassLoader());
        System.out.println("ext目录下的加载器:" + com.sun.crypto.provider.DESKeyFactory.class.getClassLoader());
        System.out.println("自己编写的代码的加载器:" + TestJDKClassloader.class.getClassLoader());

        System.out.println("引导类加载器加载一下文件:");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL);
        }

        System.out.print("extClassLoader加载以下文件:");
        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.print("appClassLoader加载以下文件:");
        System.out.println(System.getProperty("java.class.path"));
    }
}
```

输出结果：

​	![image-20220118104244024](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20220118104244024.png)

**类加载器初始化过程：**主要看com.misc.Launcher源码

sun.misc.Launcher初始化使用了单例模式设计，保证一个JVM虚拟机内只有一个 

主要存在两个类加载器：

1. sun.misc.Launcher.ExtClassLoader(扩展类加载器)
2. sun.misc.Launcher.AppClassLoader(应用类加载器)

JVM默认使用Launcher的getClassLoader()方法返回的类加载器AppClassLoader的实例加载我们 的应用程序。 

```java
public Launcher() {
        Launcher.ExtClassLoader var1;
        try {
            //构建扩展类加载器，在构造的过程中将其父类加载器设置位null
            var1 = Launcher.ExtClassLoader.getExtClassLoader();
        } catch (IOException var10) {
            throw new InternalError("Could not create extension class loader", var10);
        }
        try {
            //构造应用类加载器，在构造的过程中将其父加载器设置为ExtClassLoader
            //Launcher的loader属性值是AppClassLoader，我们一般都是用这个类加载器来加载我们自己写的应用程序
            this.loader = Launcher.AppClassLoader.getAppClassLoader(var1);
        } catch (IOException var9) {
            throw new InternalError("Could not create application class loader", var9);
        }

        Thread.currentThread().setContextClassLoader(this.loader);
        String var2 = System.getProperty("java.security.manager");
        if (var2 != null) {
            SecurityManager var3 = null;
            if (!"".equals(var2) && !"default".equals(var2)) {
                try {
                    var3 = (SecurityManager)this.loader.loadClass(var2).newInstance();
                } catch (IllegalAccessException var5) {
                } catch (InstantiationException var6) {
                } catch (ClassNotFoundException var7) {
                } catch (ClassCastException var8) {
                }
            } else {
                var3 = new SecurityManager();
            }

            if (var3 == 	null) {
                throw new InternalError("Could not create SecurityManager: " + var2);
            }

            System.setSecurityManager(var3);
        }
    }
```

**双亲委派机制**

![image-20220118105140028](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20220118105140028.png)

加载某个类时会先委托父加载器寻找目标类，找不到再 委托上层父加载器加载，如果所有父加载器在自己的加载类路径下都找不到目标类，则在自己的 类加载路径中查找并载入目标类。**说简单点就是，先找父亲加载，不行再由儿子自己加载**

应用程序类加载器AppClassLoader加载类的双亲委派机制源码，AppClassLoader 的loadClass方法最终会调用其父类ClassLoader的loadClass方法，该方法的大体逻辑如下：

1. 首先，检查一下指定名称的类是否已经加载过，如果加载过了，就不需要再加载，直接 返回
2.  如果此类没有加载过，那么，再判断一下是否有父加载器；如果有父加载器，则由父加 载器加载（即调用parent.loadClass(name, false);）.或者是调用bootstrap类加载器来加 载
3. 如果父加载器及bootstrap类加载器都没有找到指定的类，那么调用当前类加载器的findClass方法来完成类加载

ClassLoader.java

```java
protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException
{
    synchronized (getClassLoadingLock(name)) {
        //检查当前类加载器是否已经加载了该类
        Class<?> c = findLoadedClass(name);
        if (c == null) {
            long t0 = System.nanoTime();
            try {
                if (parent != null) {
                     //如果当前加载器父加载器不为空则委托父加载器加载该类
                    c = parent.loadClass(name, false);
                } else {
                    //如果当前加载器父加载器为空则委托引导类加载器加载该类
                    c = findBootstrapClassOrNull(name);
                }
            } catch (ClassNotFoundException e) {
                // ClassNotFoundException thrown if class not found
                // from the non-null parent class loader
            }

            if (c == null) {
                long t1 = System.nanoTime();
                //都会调用URLClassLoader的findClass方法在加载器的类路径里查找并加载该类
                c = findClass(name);

                // this is the defining class loader; record the stats
                sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();
            }
        }
        if (resolve) {
            resolveClass(c);
        }
        return c;
    }
}
```

**双亲委派机制设计的目的**

* 沙箱安全机制：自己写的java.lang.String.class类不会被加载，这样便可以防止核心 API库被随意篡改 
* 避免类的重复加载：当父亲已经加载了该类时，就没有必要子ClassLoader再加载一 次，保证**被加载类的唯一性** 

**全盘负责委托机制** :指当一个ClassLoder装载一个类时，除非显示的使用另外一个ClassLoder，该类所依赖及引用的类也由这个ClassLoder载入

### 3. 自定义加载器

自定义类加载器只需要继承 java.lang.ClassLoader 类，该类有两个核心方法，一个是 loadClass(String, boolean)，实现了**双亲委派机制**，还有一个方法是findClass，默认实现是空 方法，所以我们自定义类加载器主要是**重写**findClass**方法**。 

```java
public class MyClassLoaderTest1 {
    public static void main(String[] args) throws Exception {
        MyClassLoader1 classLoader = new MyClassLoader1("E:\\github\\java-interview\\java-learn\\target\\classes");
        Class clazz = classLoader.loadClass("com.hsm.java.jvm.MyClassLoaderTest1");
        Object obj = clazz.newInstance();
        System.out.println(clazz.getClassLoader().getClass().getName());
    }


    static class MyClassLoader1 extends ClassLoader {
        private String classPath;

        public MyClassLoader1(String classPath) {
            this.classPath = classPath;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                //defineClass将一个字节数组转为Class对象，这个字节数组是class文件读取后最终的字节 数组。
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                throw new ClassNotFoundException();
            }

        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }
    }
}
```

### 4. 打破双亲委托

```java
public class MyClassLoaderTest2 {
    public static void main(String[] args) throws Exception {
        MyClassLoader classLoader = new MyClassLoader("E:\\github\\java-interview\\java-learn\\target\\classes");
        Class clazz = classLoader.loadClass("com.hsm.java.jvm.MyClassLoaderTest2");
        Object obj = clazz.newInstance();
        System.out.println(clazz.getClassLoader().getClass().getName());
    }


    static class MyClassLoader extends ClassLoader {
        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                //defineClass将一个字节数组转为Class对象，这个字节数组是class文件读取后最终的字节 数组。
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                throw new ClassNotFoundException();
            }

        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            synchronized (getClassLoadingLock(name)) {
                Class<?> c = findLoadedClass(name);
                if (c == null) {
                    long t1 = System.nanoTime();

                    if(!name.startsWith("com.hsm")){
                        //非自定义的类还是走双亲委派加载
                        c = this.getParent().loadClass(name);
                    }else{
                        c = findClass(name);
                    }
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();

                }
                if (resolve) {
                    resolveClass(c);
                }
                return c;
            }
        }
    }
}
```



