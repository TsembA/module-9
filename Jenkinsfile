#!/usr/bin/env groovy

@Library('jenkins-shared-library') _
def gv

pipeline {
    agent any
    tools {
        nodejs "node"
    }
    environment {
        IMAGE_NAME = 'dancedevops/my-node-app'
    }
    stages {
        stage('INCREMENT VERSION') {
            steps {
                dir('app') {
                    script {
                        incrementVersion()
                    }
                }
            }
        }

        stage('RUN TEST') {
            steps {
                dir('app') {
                    script {
                        testApp()
                    }
                }
            }
        }

        stage('BUILD AND PUSH DOCKER IMAGE') {
            steps {
                script {
                    buildAndPushImage()
                }
            }
        }
        stage('deploy to EC2') {
            steps {
                script {
                   def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME}"
                   
                   def ec2Instance = "ec2-user@54.183.216.49"

                   sshagent(['ec2-server-key']) {
                       sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${ec2Instance}:/home/ec2-user"
                       sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${ec2Instance}:/home/ec2-user"
                       sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"
                   }     
                }
            }
        }

        stage('COMMIT VERSION UPDATE') {
            steps {
                dir('app') {
                    script {
                        commitVersionUpdate()
                    }
                }
            }
        }
    }
}
