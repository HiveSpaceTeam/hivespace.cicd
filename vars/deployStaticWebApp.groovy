import hivespace.entity.*

def call() {
    stage('Install node dependencies') {
            sh 'npm install'
    }
    stage('Build Apps') {
            sh 'npm run build'
    }
    stage('Deploy to Azure Static Web Apps') {
            script {
                def outputLocation = 'dist'
                echo "🚀 Deploying to Azure Static Web Apps from ${outputLocation}"
                sh """
                swa deploy ${outputLocation} --deployment-token $AZURE_STATIC_WEB_APPS_API_TOKEN  --env production
            """
            }
    }
}
