### 1. 环境说明

centos8

node

### 2. 安装
1. 安装**nodejs**

   ```shell
   yum install -y nodejs
   ```

2. 验证**nodejs**版本

   ```shell
   node -v # 建议版本大于 v8.12.0,低版本未做测试
   npm -v  # npm版本 
   ```

3. 安装**elasticsearch-dump**

   ```shell
   npm install elasticdump
   ```

4. 验证**elasticsearch-dump**安装

   1. 进入module目录

      ```shell
      cd node_modules/elasticdump/bin/
      ```

   2. 执行

      ```shell
      ./elasticdump
      # 出现这个错误表示安装成功
      Fri, 19 Oct 2018 07:03:15 GMT | Error Emitted => {"errors":["`input` is a required input","`output` is a required input"]}
      ```

### 3. 使用dump开始迁移索引及数据

1. 只拷贝索引

   ```shell
   elasticdump \
   	--input=http://sourceIp:9200/my_index \
   	--output=http://destinationIp:9200/my_index \
   	--type=mapping
   # 存在my_index就表示迁移单个索引
   ```

2. 拷贝数据

   ```shell
   elasticdump \
   	--input=http://sourceIp:9200/my_index \
   	--output=http://destinationIp:9200/my_index \
   	--type=data
   # 存在my_index就表示迁移单个索引数据
   ```

3. 索引数据一起拷贝

   ```shell
   elasticdump \
   	--input=http://sourceIp:9200/my_index \
   	--output=http://destinationIp:9200/my_index \
   	--type=all
   # 存在my_index就表示迁移单个索引
   ```

   

