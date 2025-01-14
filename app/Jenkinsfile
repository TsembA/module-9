#!/usr/bin/env groovy

@Library('jenkins-shared-library@main') _
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
