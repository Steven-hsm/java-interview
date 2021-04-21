### docker 安装

**注意事项**

* 先确定es（elastiSearch）的版本

1. 镜像拉取

   ```shell
   docker pull docker.io/kibana:6.8.15
   ```

2. 创建目录，用于挂在kibana容器目录

   ```shell
   mkdir -p /work/docker/kibana/standalone/usr/share/kibana/config
   ```

3. 创建临时容器，将里面的配置拷贝到挂在容器目录内

   ```shell
   docker run -d --name tmp_kibana docker.io/kibana:6.8.15
   docker cp tmp_kibana:/usr/share/kibana/config /work/docker/kibana/standalone/usr/share/kibana/config
   ```

4. 删除临时容器

   ```shell
   docker stop tmp_kibana; docker rm tmp_kibana    #删除临时容器
   ```

5. 修改配置文件

   ```shell
   vi /work/docker/kibana/standalone/usr/share/kibana/config/kibana.yml 
   
   server.port: 5601
   server.host: 0.0.0.0
   elasticsearch.hosts: [ "http://192.168.0.52:9200" ]
   i18n.locale: "zh-CN"
   xpack.monitoring.ui.container.elasticsearch.enabled: true
   ```

6. 编写启动脚本``` start_kibana.sh```,方便记住挂载目录，启动配置等

   ```shell
   docker stop mykibana
   docker rm mykibana
   docker run -d --name mykibana -p 5601:5601 \
   -v /work/docker/kibana/standalone/usr/share/kibana/config:/usr/share/kibana/config \
   -v /etc/localtime:/etc/localtime \
   docker.io/kibana:6.8.15
   ```

7. 启动

   ```shell
   chmod +777 start_kibana.sh
   ./start_kibana.sh
   ```

8. 访问 http://本机ip:5601

