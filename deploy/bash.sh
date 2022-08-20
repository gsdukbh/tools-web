# bin/bash
cd /tools
docker-compose down
docker rmi gsdukbh/tools-web:latest
docker-compose up -d