import hivespace.entity.*

def call() {
    stages {
        stage('Install node dependencies') {
            steps {
                sh 'npm install'
            }
        }
        stage('Build Apps') {
            steps {
                sh 'npm run build'
            }
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
}
