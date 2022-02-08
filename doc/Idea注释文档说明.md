### 1.鲲鹏注释规约

1. <font color=red>强制 </font>类、类属性、类方法的注释必须使用 Javadoc 规范，使用`/**内容*/格式`，不得使用// xxx 方式。

   > 在 IDE 编辑窗口中，Javadoc 方式会提示相关注释，生成 Javadoc 可以正确输出相应注释；在 IDE中，工程调用方法时，不进入方法即可悬浮提示方法、参数、返回值的意义，提高阅读效率。

2. <font color=red>强制 </font>所有的抽象方法（包括接口中的方法）必须要用 Javadoc 注释、除了返回值、参数、异常说明外，还必须指出该方法做什么事情，实现什么功能。

   > 所有的抽象方法（包括接口中的方法）必须要用 Javadoc 注释、除了返回值、参数、异常说明外，还必须指出该方法做什么事情，实现什么功能

3. <font color=red>强制 </font>所有的类都必须添加创建者和创建日期。

   > 使用注释模板

4. <font color=red>强制 </font>方法内部单行注释，在被注释语句上方另起一行，使用//注释。方法内部多行注释使用/* */注释，注意与代码对齐

5. <font color=red>强制 </font>所有的枚举类型字段必须要有注释，说明每个数据项的用途

6. <font color=yellow>推荐 </font>与其“半吊子”英文来注释，不如用中文注释把问题说清楚。专有名词与关键字保持英文原文即可

   > “TCP 连接超时”解释成“传输控制协议连接超时”，理解反而费脑筋

7. <font color=yellow>推荐 </font>代码修改的同时，注释也要进行相应的修改，尤其是参数、返回值、异常、核心逻辑等的修改。

8. <font color=yellow>推荐 </font>在类中删除未使用的任何字段和方法；在方法中删除未使用的任何参数声明与内部变量

9. <font color=green>参考</font> 谨慎注释掉代码。在上方详细说明，而不是简单地注释掉。如果无用，则删除。

10. <font color=green>参考</font> 对于注释的要求：第一、能够准确反映设计思想和代码逻辑；第二、能够描述业务含义，使别的程序员能够迅速了解到代码背后的信息。完全没有注释的大段代码对于阅读者形同天书，注释是给自己看的，即使隔很长时间，也能清晰理解当时的思路；注释也是给继任者看的，使其能够快速接替自己的工作

11. <font color=green>参考</font> 好的命名、代码结构是自解释的，注释力求精简准确、表达到位。避免出现注释的一个极端：过多过滥的注释，代码的逻辑一旦修改，修改注释是相当大的负担。

12. <font color=green>参考</font> 特殊注释标记，请注明标记人与标记时间。注意及时处理这些标记，通过标记扫描，经常清理此类标记。线上故障有时候就是来源于这些标记处的代码。
    1. 待办事宜（TODO）:（标记人，标记时间，[预计处理时间]）
    2. 错误，不能工作（FIXME）:（标记人，标记时间，[预计处理时间]）
### 2. IDEA注释模板使用

http://showdoc.local.yjzhixue.com:4999/web/#/10?page_id=557

注释效果（setting.zip文件在showdoc会一直更新最新版本）：

![](http://showdoc.local.yjzhixue.com:4999/server/index.php?s=/api/attachment/visitFile/sign/19212b5f58dc924beb2f6b99bb499faa)

1. 导入文件setting.zip

     路径 FIle ->Manage IDE Settings ->Import Settings

     ![](http://showdoc.local.yjzhixue.com:4999/server/index.php?s=/api/attachment/visitFile/sign/8002b1e4e483a7f08da8c681975b6c33)
   
2. 使用

   默认的扩展方式可以在这里设置

   ![](http://showdoc.local.yjzhixue.com:4999/server/index.php?s=/api/attachment/visitFile/sign/177ea3015c4896c41b74eaaf544249e8)
   
   * 类注释

     * 创建类时自动增加注释
     * 输入 `/* + enter` 可以自动出现注释
     * 注释demo如下：TODO为需要修改的类描述符
     * <font color=red>author获取的是操作系统账号信息，如果无法识别个人信息，这里可以直接写成自己的git账号用户名</font>
   
     ```java
     /**
      * Copyright © 2017 - 2022 深圳鲲鹏云智教育科技有限公司
      * TODO 
      * @author ${USER}
      * @date ${DATE} ${TIME}
      * @version 1.0
      */
     ```

   * 方式注释
   
     * 在方法上输入 `/** + enter` 可以自动出现注释
   
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

### 3. IDEA 注释修改方法

#### 3.1 创建类时自动添加类注释

1.  进入类注释修改页面：FIle -> Settings ->Editor -> File and Code Templates -> Class

  ![](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20220114093914118.png)

2. 增加上面的注释后点击`保存`按钮

   ```java
   /**
    * Copyright © 2017 - 2022 深圳鲲鹏云智教育科技有限公司
    * TODO 
    * @author ${USER}
    * @date ${DATE} ${TIME}
    * @version 1.0
    */
   ```

#### 3.2 通过前缀添加注释

1. 进入类注释修改页面：FIle -> Settings ->Editor -> Live Templates

   ![](http://showdoc.local.yjzhixue.com:4999/server/index.php?s=/api/attachment/visitFile/sign/7ce82849bc1099a821b4d17e8a4ccdf9)

2. 可以看到我这里添加了kp-java-template组，以及两个缩写 `*`以及`**`。这里不要在前面加 `/`,否则注释会出问题

3. `*` 类上注解

   ```java
   **
    * Copyright © 2017 - 2022 深圳鲲鹏云智教育科技有限公司
    * TODO
    * @author $user$
    * @date $date$ $time$
    * @version 1.0
    */
   ```

   上面的内容有很多的变量，所以我们需要点击`Edit variables`指定变量的值

   ![](http://showdoc.local.yjzhixue.com:4999/server/index.php?s=/api/attachment/visitFile/sign/3bc2b894249f41011f453c758395dcc5)

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

    ![](http://showdoc.local.yjzhixue.com:4999/server/index.php?s=/api/attachment/visitFile/sign/2e8b412651d6f44aaeb4c73fe7159d19)

   可以看到我们对`params`,`returns`做了特殊处理，这里是groovy脚本，有兴趣的可以改进一下

   * params 脚本

     ```groovy
     groovyScript("def result=''; def params=\"${_1}\".replaceAll('[\\\\[|\\\\]|\\\\s]', '').split(',').toList(); for(i = 0; i < params.size(); i++) {if(i==0) {result+='* @param ' + params[i] + ((i < params.size() - 1) ? '\\r\\n' : '')}else{result+=' * @param ' + params[i] + ((i < params.size() - 1) ? '\\r\\n' : '')}}; return result", methodParameters())
     ```

   * returns 脚本

     ```shell
     groovyScript("return \"${_1}\" == 'void' ? '' : '* @return {@link ' +\"${_1}\" + '}'", methodReturnType())
     ```

### 4. 导出自己的配置给别人用

如果觉得自己的配置很好，可以修改之后导出自己的配置给别人使用

![](http://showdoc.local.yjzhixue.com:4999/server/index.php?s=/api/attachment/visitFile/sign/8be2313b3bbcb6329125a763cd8a7a83)

选择注释相关文档即可

![](http://showdoc.local.yjzhixue.com:4999/server/index.php?s=/api/attachment/visitFile/sign/3dcc38ddfd10a5930c05112814e263ef)

