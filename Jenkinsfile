#!/usr/bin/env groovy

@Library('jenkins-shared-library') _
def gv

pipeline {
    agent any
    tools {
        nodejs "node"
    }
    stages {
        stage('INCREMENT VERSION') {
            steps {
                sshagent(['ec2-server-key']) {
                    sh '''
                    ssh -o StrictHostKeyChecking=no ec2-user@<your-ec2-public-ip> << EOF
                    echo "Deploying to EC2..."
                    # Add your deployment commands here, e.g., pulling code, running scripts
                    EOF
                    '''
                }
            }
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
