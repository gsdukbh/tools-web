# bin/bash
cd /tools
docker compose down
docker rmi gsdukbh/tools-web
docker build -t $IMAGE_TAG -f Dockerfile ./
docker compose up -d