#!/usr/bin/env groovy

@Library('jenkins-shared-library')
def gv

pipeline {
    agent any
    tools {
        nodejs "node"
    }
    
    environment {
        TAG = "2.0.0"
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
                    buildAndPushImage(env.IMAGE_NAME)
                }
            }
        }
        
        stage('deploy to EC2') {
            steps {
                script {
                   withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                       def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME}"
                       def ec2Instance = "ec2-user@18.144.84.255"

                       sshagent(['ec2-server-key']) {
                           sh "echo 'Docker login...'"
                           sh 'echo $PASS| docker login -u $USER --password-stdin'
                           sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${ec2Instance}:/home/ec2-user"
                           sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${ec2Instance}:/home/ec2-user"
                           
                           sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"
                       }
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
