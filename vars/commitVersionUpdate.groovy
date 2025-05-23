#!/usr/bin/env groovy

def call() {
    withCredentials([usernamePassword(credentialsId: 'github-credentials-token', usernameVariable: 'USER', passwordVariable: 'TOKEN')]) {
        script {
            sh 'git config --global user.email "tsembenhoi@gmail.com"'
            sh 'git config --global user.name "TsembA"'
            sh 'git remote set-url origin https://${USER}:${TOKEN}@github.com/TsembA/module-9'
            sh '''
            if ! git diff --cached --exit-code > /dev/null; then
                git commit -m "ci: version bump"
                git push origin HEAD:jenkins-jobs
            else
                echo "No changes to commit. Happy New Year"
            fi
            '''
        }
    }
}