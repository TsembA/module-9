#!/usr/bin/env
export NAME=${IMAGE_NAME}
docker-compose -f docker-compose.yaml up --detach
echo "success"
