#### 1. 安装

1. 下载安装包

   ```shell
   wget https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-rhel80-4.4.1.tgz
   ```

2. 解压

   ```shell
   tar -zxvf mongodb-linux-x86_64-rhel80-4.4.1.tgz #解压
   ```

3. 重命名

   ```shell
   mv mongodb-linux-x86_64-rhel80-4.4.1  /opt/mongodb4  #移动文件
   ```

4. 配置环境变量

   ```shell
   export PATH=/opt/mongodb4/bin:$PATH
   ```

5. 新建目录，存储数据和日志

   ```shell
   sudo mkdir -p /usr/mongodb/data
   sudo mkdir -p /usr/mongodb/log
   ```

6. 修改配置文件‘

   ```shell
   vi mongodb.conf
   
   dbpath=/usr/mongodb/data #数据文件存放目录
   logpath=/usr/mongodb/log/mongod.log #日志文件存放地址
   port=27017 #端口
   fork= true #以守护程序的方式启用，即在后台运行
   #auth=true #需要认证。如果放开注释，就必须创建MongoDB的账号，使用账号与密码才可远程访问，第一次安装建议注释
   bind_ip=0.0.0.0 #允许远程访问，或者直接注释，127.0.0.1是只允许本地访问
   ```

7. 启动MongoDB

   ```shell
   mongod  -f  /opt/mongodb4/mongodb.conf  #启动mongodb
   ```

8. 若数据损坏，需要进行如下操作

   ```shell
   rm -f /mongodb/single/data/db/*.lock # 删除lock文件
   /usr/local/mongdb/bin/mongod --repair --dbpath=/mongodb/single/data/db #修复数据
   ```

#### 2. 基本命令

1. 登录到mongo

   ```shell
   //客户端登录服务，注意，这里通过localhost登录，如果需要远程登录，必须先登录认证才行。 
   mongo --port 27017 
   //#切换到admin库 
   use admin 
   ```

2. 数据库操作

   ```shell
   use blog #创建数据库
   show dbs | show databases # 查看所有的数据库
   db	#查看当前正在使用的mongo
   db.dropDatabase() # 删除数据库
   ```

3. 集合操作

   ```shell
   db.createCollection("user") #创建用户集合
   show collections | show tables #查看所有的集合
   db.user.drop #删除集合
   ```

4. 文档基本操作

   ```shell
   # 1. 插入操作
   db.user.insert(
   	{"age":20,"name":"张三","email":"123456789@qq.com"}, #插入的文档
   	{
   		# 可选"writeConcern":
   		ordered:true #可选。如果为真，则按顺序插入数组中的文档，如果其中一个文档出现错误，MongoDB将返回而不处理数组中的其余文档。如果为假，则执行无序插入，如果其中一个文档出现错误，则继续处理数组中的主文档。在版本2.6+中默认为true
   	}
   )
   
   db.user.insert(
   	{"age":20,"name":"张三","email":"123456789@qq.com"},
   	{
   		ordered:true 
   	}
   )
   # 2.查询操作
   db.collection.find(
   	<query>, #可选。使用查询运算符指定选择筛选器
   	[projection]) #可选。指定要在与查询筛选器匹配的文档中返回的字段（投影）。
   
   db.user.find({"age"：20})
   db.user.find({"age":20},{age:1,_id:0})
   
   # 3.更新操作
   db.collection.update(
   query, # 更新的选择条件
   update, #要应用的修改
   options)
   options{
   	upsert # 可选。如果设置为true，则在没有与查询条件匹配的文档时创建新文档。默认值为false，如果找不到匹配项，则不会插入新文档
   	multi # 可选。如果设置为true，则更新符合查询条件的多个文档。如果设置为false，则更新一个文档。默认值为false。
   }
   
   db.user.update({age:20},{age:21}) # 除了age，其他字段都会被更新掉
   db.user.insert({"age":29,"name":"李四","email":"987654321@qq.com"})
   db.user.update({"age":29},{$set:{"age":30}}) # 局部更新
   
   # 4.删除文档
   db.collection.remove(条件)
   db.user.remove({"age":21})
   ```

5. 分页查询

   ```shell
   db.user.count() #统计集合的记录数
   
   db.collection.find().limit(number1).skip(number2) #分页查询
   db.user.find().limit(2)
   ```

6. 高级查询

   ```shell
   db.collection.find({field:/正则表达式/}) # 正则表达式查询
   db.user.find({"name":/李/})
   
   db.user.find({age:{$gt:28}}) #比较查询 < $lt,<= $lte,> $gt,>= $gte ，!= $ne
   
   db.user.find({age:{$in:[28,29]}}) # 包含查询
   
   db.user.insert({"age":28,"name":"张三","email":"987654321@qq.com"})
   db.user.find({$and:[{age:28},{name:"张三"}]} # 条件连接查询 $and $or
   
   ```

#### 3. 索引

1. 索引类型

    * 单字段索引
    * 复合索引
    * 其他索引：地理空间索引（Geospatial Index）、文本索引（Text Indexes）、哈希索引（Hashed Indexes）

2. 基本命令操作

   ```shell
   db.user.getIndexes() # 返回集合中存在的索引
   
   db.collection.createIndex(
   keys, #包含字段和值对的文档，其中字段是索引键，值描述该字段的索引类型。对于字段上的升序索引，请指定值1；对于降序索引，请指定值-1
   options) # 包含一组控制索引创建的选项的文档
   db.user.createIndex({age:1})
   
   db.user.dropIndex({age:1})#删除索引
   
   db.collection.dropIndexes() #删除所有索引
   ```

3. 索引的使用

   ```shell
   db.collection.find().explain() #分析查询性能
   
   #当查询条件和查询的投影仅包含索引字段时，MongoDB直接从索引返回结果，而不扫描任何文档或将文档带入内存
   ```



4. 