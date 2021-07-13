docker stop brain && docker rm brain
docker build -t steven/brain .
docker run -d -p 9090:9090 --name brain steven/brain
docker logs -f brain