# bin/bash
cd /tools
if docker compose ps | grep -q 'Up'; then
    docker compose down
    docker rmi gsdukbh/tools-web
    docker build -t $IMAGE_TAG -f Dockerfile ./
    docker compose up -d
else
    docker build -t $IMAGE_TAG -f Dockerfile ./
    docker compose up -d
fi