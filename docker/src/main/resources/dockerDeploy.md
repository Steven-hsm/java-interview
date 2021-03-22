```shell
# jenkins 用户的id为1000
chown -R 1000:1000 /data/deploy/dockersh/
#!/bin/bash
docker stop jenkins
docker rm jenkins
docker run -itd -p 9090:8080 -p 50000:50000 --name jenkins  -v /var/run/docker.sock:/var/run/docker.sock -v $(pwd)/jenkins:/var/jenkins_home  jenkinsci/blueocean

```