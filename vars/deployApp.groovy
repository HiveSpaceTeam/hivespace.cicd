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
                    echo '📦 Starting Azure Static Web App deployment process...'
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
            stage('Build the App') {
                steps {
                    sh 'npm run build'
                }
            }
            stage('Deploy to Azure Static Web Apps') {
                steps {
                    script {
                        // def app = project.apps[0]
                        def outputLocation = 'dist'

                        echo "🚀 Deploying to Azure Static Web Apps from ${outputLocation}"
                        sh """
                            swa deploy ${outputLocation} --deployment-token $AZURE_STATIC_WEB_APPS_API_TOKEN  --env production
                        """
                    // OR: Use Azure CLI if available
                    // sh "az staticwebapp upload --name ${app.azureAppName} --source ${outputLocation} --token $AZURE_STATIC_WEB_APPS_API_TOKEN"
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
