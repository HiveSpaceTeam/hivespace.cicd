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
                        git branch: project.branch, url: project.gitRepo
                    }
                }
            }
            stage('Install node dependencies') {
                steps {
                    sh 'npm install'
                }
            }
            stage('Build Apps') {
                // steps {
                //     sh 'npm run build'
                // }
                buildApps(project, project.branch)
            }
            stage('Deploy to Azure Static Web Apps') {
                steps {
                    script {
                        def outputLocation = 'dist'
                        echo "🚀 Deploying to Azure Static Web Apps from ${outputLocation}"
                        sh """
                            swa deploy ${outputLocation} --deployment-token $AZURE_STATIC_WEB_APPS_API_TOKEN  --env production
                        """
                    }
                }
            }
        }

        post {
            success {
                echo '✅ Deployment to Azure Static Web Apps succeeded.'
            }
            failure {
                echo '❌ Deployment to Azure Static Web Apps failed.'
            }
        }
    }
}
