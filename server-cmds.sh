
export IMAGE_NAME="dancedevops/my-node-app:$1"

# Login to Docker registry
echo $PASS | docker login -u $USER --password-stdin

# Pull the latest image
docker pull $IMAGE_NAME

# Run docker-compose
docker-compose up -d
