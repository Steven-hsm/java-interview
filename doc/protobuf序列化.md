## 1.根据proto文件生成 java文件

1. 安装protoc命令

   下载地址：https://github.com/protocolbuffers/protobuf/releases 

   如果想快捷使用，请配置相关的环境变量

2. 执行以下命令，可以保存为脚本一件执行

```bat
protoc --proto_path=E:\github\java-interview\java-learn\src\main\java\com\hsm\java\serialize\protobuf --java_out=E:\github\java-interview\java-learn\src\main\java  E:\github\java-interview\java-learn\src\main\java\com\hsm\java\serialize\protobuf\*.proto
```

* protoc ：命令
* --proto_path：proto文件路径
* --java_out：java文件保存地址
* \*.proto：需要生成的文件

3. idea插件

   尝试了很多的方式，都以失败告终。有兴趣的可以自己再尝试以下

## 2. 使用

1. NettyMessageProToBuf.proto

   ```protobuf
   syntax = "proto3";
   option java_package = "com.hsm.java.serialize.protobuf";
   option java_outer_classname = "NettyMessage";
   
   message NettyMessageProToBuff{
     string requestId = 1;
     int32 msgType = 2;
     string data = 3;
   }
   ```

   将文件放到目录下，执行脚本就可以生成对应的java文件到目录下

2. 使用

   ```java
   @Slf4j
   public class Main {
       public static void main(String[] args) throws InvalidProtocolBufferException {
           NettyMessage.NettyMessageProToBuff.Builder builder = NettyMessage.NettyMessageProToBuff.newBuilder();
           builder.setRequestId("q");
           builder.setMsgType(1);
           builder.setData("a");
           
           NettyMessage.NettyMessageProToBuff build = builder.build();
           log.info("build数据：{}" , build.toByteArray());
   //        NettyMessage.NettyMessageProToBuff nettyMessageProToBuff = NettyMessage.NettyMessageProToBuff.parseFrom(build.toByteArray());
           //log.info("数据：{}" , JSON.toJSONString(nettyMessageProToBuff));
   		
           //这里和json序列化对比
           JsonData jsonData = new JsonData();
           jsonData.setRequestId("q");
           jsonData.setMsgType(1);
           jsonData.setData("a");
           log.info("Jsondata：{}" , JSON.toJSONString(jsonData).getBytes(StandardCharsets.UTF_8));
       }
   }
   ```

   对比数据结果:

   ```she
   build数据：[10, 1, 113, 16, 1, 26, 1, 97]
   Jsondata：[123, 34, 100, 97, 116, 97, 34, 58, 34, 97, 34, 44, 34, 109, 115, 103, 84, 121, 112, 101, 34, 58, 49, 44, 34, 114, 101, 113, 117, 101, 115, 116, 73, 100, 34, 58, 34, 113, 34, 125]
   ```

   可以明显看到json格式后的二进制文件比 protobuf文件大很多

## 3. protobuf为何这么快

[参考博客](https://www.jianshu.com/p/e0d81a9963e9)

protobuf采用编号的形式，将字段名省去，通过编号的方式与数据一一对应。编号用tag表述

二进制格式就是 tag|value tage|value

每个消息项前面都会有对应的tag，才能解析对应的数据类型，表示tag的数据类型也是Varint。

* tag的计算方式: (field_number << 3) | wire_type

每种数据类型都有对应的wire_type:

| **Wire Type** | **Meaning Used For**                                         |
| ------------- | ------------------------------------------------------------ |
| 0             | Varint int32, int64, uint32, uint64, sint32, sint64, bool, enum |
| 1             | 64-bit fixed64, sfixed64, double                             |
| 2             | Length-delimited string, bytes, embedded messages, packed repeated fields |
| 3             | Start group groups (deprecated)                              |
| 4             | End group groups (deprecated)                                |
| 5             | 32-bit fixed32, sfixed32, float                              |

比如下面数据

```protobuf
message NettyMessageProToBuff{
  string requestId = 1; //q
  int32 msgType = 2;//1
  string data = 3;//a
}
```

对应的tag分别为：

* 1 <<<3  |  2 = 10
* 2 <<<3  |  0  = 16
* 3 <<<3  |  2  = 26

另外string 类型不确定字符长度，所以需要一个长度标识位，最终的结果为 build数据：[10, 1, 113, 16, 1, 26, 1, 97]

