#!/usr/bin/env groovy

def call(String IMAGE_NAME) {

    if (!IMAGE_NAME) {
        error("IMAGE_NAME is not set. Please provide a valid image name or version.")
    }

    withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
        script {
            sh "docker build -t dancedevops/my-node-app:${IMAGE_NAME} -f Dockerfile /var/jenkins_home/workspace/my-pipe"
            sh 'echo "$PASS" | docker login --username "$USER" --password-stdin'
            sh "docker push dancedevops/my-node-app:${IMAGE_NAME}"
        }
    }
}
