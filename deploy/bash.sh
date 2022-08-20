# bin/bash
cd /tools/deploy
docker-compose down
docker pull gsdukbh/tools-web:latest
docker-compose up -d