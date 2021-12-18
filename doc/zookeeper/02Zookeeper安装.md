依赖java环境，请先配好java环境

wget https://downloads.apache.org/zookeeper/stable/apache-zookeeper-3.6.3-bin.tar.gz

tar -zxvf apache-zookeeper-3.6.3-bin.tar.gz

cd apache-zookeeper-3.6.3-bin

cp conf/zoo_sample.cfg conf/zoo.cfg

bin/zkServer.sh start conf/zoo.cfg 

