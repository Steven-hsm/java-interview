### 1. IDEA注释模板使用

1. 导入文件setting.zip

   * 文件下载地址

     >链接：https://pan.baidu.com/s/133g7UJQeLE_gebrMHg6-yw 
     >提取码：b4r9

   * Idea导入方式

     路径 FIle ->Manage IDE Settings ->Import Settings

     ![image-20211230181149722](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211230181149722.png)

2. 使用

   * 类注释

     * 创建类时自动增加注释
     * 输入 `/* + Tab` 可以自动出现注释
     * 注释demo如下：TODO为需要修改的类描述符

     ```java
     /**
      * TODO
      * @author steven
      * @date 2021/12/30 15:22
      * @version 1.0
      */
     ```

   * 方式注释

     * 在方法上输入 `/** + Tab` 可以自动出现注释

     * 注释demo如下：TODO为需要修改的类描述，这个存在一个问题，return 后面如果是List,会有一个空格，没有找到解决方案

       ```java
       /** 
          * TODO
          * @param ddlQuestionId
          * @param ksDdlSetting 
          * @return {@link List< QuestionES>}
          * @author steven
          * @date 2021/12/30 18:06
          */ 
       ```

### 2. IDEA 注释修改方法

#### 2.1 创建类时自动添加类注释

1.  进入类注释修改页面：FIle -> Settings ->Editor -> File and Code Templates -> Class

   ![image-20211230182211913](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211230182211913.png)

2. 增加上面的注释后点击`保存`按钮

   ```java
   /**
    * TODO 
    * @author ${USER}
    * @date ${DATE} ${TIME}
    * @version 1.0
    */
   ```

#### 2.2 通过前缀添加注释

1. 进入类注释修改页面：FIle -> Settings ->Editor -> Live Templates

   ![image-20211230182700206](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211230182700206.png)

2. 可以看到我这里添加了kp-java-template组，以及两个缩写 `*`以及`**`。这里不要在前面加 `/`,否则注释会出问题

3. `*` 类上注解

   ```java
   **
    * TODO
    * @author $user$
    * @date $date$ $time$
    * @version 1.0
    */
   ```

   上面的内容有很多的变量，所以我们需要点击`Edit variables`指定变量的值

   ![image-20211230190411402](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211230190411402.png)

4.  ** 方法上注解

   ```java
   ** 
    * TODO
    $params$ 
    $returns$
    * @author $USER$
    * @date $date$ $time$
    */ 
   
   ```

   同样，这里也有变量需要为变量赋值

   ![image-20211230190514669](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211230190514669.png)

   可以看到我们对`params`,`returns`做了特殊处理，这里是groovy脚本，有兴趣的可以改进一下

   * params 脚本

     ```groovy
     groovyScript("def result=''; def params=\"${_1}\".replaceAll('[\\\\[|\\\\]|\\\\s]', '').split(',').toList(); for(i = 0; i < params.size(); i++) {if(i==0) {result+='* @param ' + params[i] + ((i < params.size() - 1) ? '\\r\\n' : '')}else{result+=' * @param ' + params[i] + ((i < params.size() - 1) ? '\\r\\n' : '')}}; return result", methodParameters())
     ```

   * returns 脚本

     ```shell
     groovyScript("return \"${_1}\" == 'void' ? '' : '* @return {@link ' +\"${_1}\" + '}'", methodReturnType())
     ```

### 3. 导出自己的配置给别人用

如果觉得自己的配置很好，可以修改之后导出自己的配置给别人使用

![image-20211230190940604](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211230190940604.png)

选择注释相关文档即可

![image-20211230191026174](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20211230191026174.png)