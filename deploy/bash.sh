# bin/bash
cd /tools/deploy
docker-compose down
docker rmi gsdukbh/tools-web:latest
docker pull gsdukbh/tools-web:latest
docker-compose up -d