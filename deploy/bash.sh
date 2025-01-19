# bin/bash
cd /tools
if docker compose ps | grep -q 'Up'; then
    echo "Tools is running, stop and restart"
    docker compose down
    docker rmi gsdukbh/tools-web
    docker buildx build -t $IMAGE_TAG -f Dockerfile ./
    docker compose up -d
else
    echo "Tools is not running, start"
    docker buildx build -t $IMAGE_TAG -f Dockerfile ./
    docker compose up -d
fi