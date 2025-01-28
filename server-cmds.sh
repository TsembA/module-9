#!/usr/bin/env groovy
export IMAGE_NAME="dancedevops/my-node-app:$1"
echo $PASS | docker login -u $USER --password-stdin
docker pull $IMAGE_NAME
docker-compose up -d
