#!/usr/bin/env groovy

@Library('jenkins-shared-library')
def gv

pipeline {
    agent any
    tools {
        nodejs "node"
    }
    environment {
        IMAGE_NAME = ""
        TAG = "${BUILD_NUMBER}"
    }
    
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
                   withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                       def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME}"
                       def ec2Instance = "ec2-user@3.101.47.1"

                       sshagent(['ec2-server-key']) {
                           // Docker login command with secure password passing
                           sh "echo 'Docker login...'"
                           sh 'echo $PASS| docker login -u $USER --password-stdin'

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
