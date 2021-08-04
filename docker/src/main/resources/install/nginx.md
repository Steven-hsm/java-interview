mkdir -p /usr/nginx/conf/ /usr/nginx/html /usr/nginx/logs
# 

docker run -d --name nginx \
--restart always \
--net host \
-v /usr/nginx/html:/usr/share/nginx/html \
-v /usr/nginx/logs:/var/log/nginx \
-v /usr/nginx/conf:/etc/nginx  \
nginx