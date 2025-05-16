import hivespace.entity.*

def call(HiveSpaceProject project, String tag = "${env.BUILD_NUMBER}") {
    pipeline {
        agent {
            node {
                label 'UbuntuSlave01'
            }
        }
        environment {
            AZURE_STATIC_WEB_APPS_API_TOKEN = credentials('azure-static-web-apps-deploy-token')
        }
        stages {
            stage('Notify that the build has started') {
                steps {
                    echo '📦Build has started...'
                }
            }
            stage('Checkout') {
                steps {
                    script {
                        git branch: params.BRANCH, url: project.gitRepo
                    }
                }
            }

            stage('Build and Deploy') {
                steps {
                    script {
                        if (true) {
                            deployStaticWebApp()
                        }
                        // buildApps(project)
                    }
                }
            }
        }

        post {
            success {
                echo '✅ DONE.'
            }
            failure {
                echo '❌ FAILED.'
            }
        }
    }
}
