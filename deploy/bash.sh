# bin/bash
cd /tools/deploy
docker-compose down
docker rmi gsdukbh/tools-web
docker pull gsdukbh/tools-web
docker-compose up -d