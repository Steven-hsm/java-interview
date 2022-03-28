1. 删除Docker中所有已经停止的容器

   ```shell
   #获取所有的停止容器的容器id
   docker ps -a | grep Exited | awk '{print $1}'
   docker ps -qf status=exited # docker ps -a -q 
   #删除Docker中所有已经停止的容器
   docker rm `docker ps -a|grep Exited|awk '{print $1}'`
   docker rm $(sudo docker ps -qf status=exited)
   docker container prune
   ```

   

2. 