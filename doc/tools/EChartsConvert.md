##  PhantomJS在服务端生成ECharts图片

### 1. 准备工作

下载 PhantomJS包 [点我进入下载页面](https://phantomjs.org/download.html)

下载saintlee-echartsconvert-master包 [点我进入下载页面](https://gitee.com/saintlee/echartsconvert)

上传到linux服务器上

![image-20210629172521046](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20210629172521046.png)

### 2. 部署服务

1. 安装PhantomJS

   ```shell
   # 解压缩文件
   tar xjf phantomjs-2.1.1-linux-x86_64.tar.bz2
   # 创建连接
   ln -s /tools/echartsconvert/phantomjs-2.1.1-linux-x86_64/bin/phantomjs /usr/bin/phantomjs 
   # 安装字体配置
   yum -y install fontconfig
   # 验证是否安装成功
   phantomjs --version
   ```

2. 运行eschart

   ```shell
   # Echarts支持中文
   yum -y install bitmap-fonts bitmap-fonts-cjk
   # 解压缩
   unzip saintlee-echartsconvert-master.zip
   # 启动
   phantomjs /tools/echartsconvert/echartsconvert/echarts-convert.js -s -p 9090
   ```

### 3. java环境运行

pom.xml

```xml
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5.11</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.69</version>
</dependency>
```
EChartsConvertTest.java

```java
public class EChartsConvertTest {
    public static void main(String[] args) {
        String url = "http://192.168.0.164:9090";
        // 不必要的空格最好删除，字符串请求过程中会将空格转码成+号
        String optJson = "{title:{text:'ECharts 示例'},tooltip:{},legend:{data:['销量']},"
                + "xAxis:{data:['衬衫','羊毛衫','雪纺衫','裤子','高跟鞋','袜子']},yAxis:{},"
                + "series:[{name:'销量',type:'bar',data:[5,20,36,10,10,20]}]}";
        Map<String, String> map = new HashMap<>();
        map.put("opt", optJson);
        try {
            String post = post(url, map, "utf-8");
            JSONObject jsonObject = JSON.parseObject(post);
            String data = jsonObject.getString("data");
            ImageUtil.base64StringToImage(data,"1.png");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    // post请求
    public static String post(String url, Map<String, String> map, String encoding) throws ParseException, IOException {
        String body = "";

        // 创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 装填参数
        List<NameValuePair> nvps = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        // 设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        // 获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        // 释放链接
        response.close();
        return body;
    }
}
```

ImageUtil.java
```java
public static void base64StringToImage(String base64String,String filePath) {
    BASE64Decoder decoder = new BASE64Decoder();
    try {
        byte[] bytes1 = decoder.decodeBuffer(base64String);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
        BufferedImage bi1 = ImageIO.read(bais);
        File f1 = new File(filePath);
        ImageIO.write(bi1, "png", f1);
    } catch (IOException e) {
        log.error("文件生成异常",e);
    }
}
```

### 服务也可做成docker容器

服务目录如下

![image-20210629180510630](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20210629180510630.png)

Dockerfile文件

```dockerfile
#01.指定基础镜像，并且必须是第一条指令
FROM centos:7
#02.指明该镜像的作者和其电子邮件
MAINTAINER chenlin "chenlinxtu@163.com"
#03.在构建镜像时，指定镜像的工作目录，之后的命令都是基于此工作目录，如果不存在，则会创建目录
WORKDIR /work/soft
#04.添加文件
#会自动解压
ADD phantomjs-2.1.1-linux-x86_64.tar.bz2 /work/soft
#不会自动解压
ADD saintlee-echartsconvert-master.zip /work/soft
#05.安装软件
RUN ln -s /work/soft/phantomjs-2.1.1-linux-x86_64/bin/phantomjs /usr/bin/phantomjs \
 && yum -y install fontconfig \
 && yum -y install unzip \
 && unzip saintlee-echartsconvert-master.zip
#06.Echarts支持中文
RUN yum -y install bitmap-fonts bitmap-fonts-cjk
#07.配置环境变量
ENV PJS_HOME=/work/soft/phantomjs-2.1.1-linux-x86_64
ENV PATH=$PATH:$PJS_HOME/bin
#08.运行软件
ENTRYPOINT ["phantomjs", "/work/soft/echartsconvert/echarts-convert.js", "-s", "-p", "9090"]
#09.暴露端口
EXPOSE 9090
```

在dockerfile目录下运行

```shell
#创建镜像
docker build -t test/echartsconvert:0.0.1 .
# 运行镜像
docker run -it test/echartsconvert:0.0.1 /bin/bash
```



