#!/usr/bin/env groovy

@Library('jenkins-shared-library')
def gv

pipeline {
    agent any
    tools {
        nodejs "node"
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
                   // Ensure proper scoping of credentials block
                   withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                       def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME}"
                       def ec2Instance = "ec2-user@54.215.243.64"

                       sshagent(['ec2-server-key']) {
                           // Docker login command with secure password passing
                           sh "echo 'Docker login...'"
                           sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USER --password-stdin'

                           // Copy necessary files to EC2
                           sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${ec2Instance}:/home/ec2-user"
                           sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${ec2Instance}:/home/ec2-user"
                           
                           // Execute commands on EC2
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
