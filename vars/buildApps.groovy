import hivespace.entity.*
import hivespace.constants.HiveSpaceConstants

def call(HiveSpaceProject project) {
    project.apps.each { app ->
        echo "build apps  buildFrameworkType: ${app.buildFrameworkType} - ${HiveSpaceConstants.netCore} - ${HiveSpaceConstants.nodeJsSWA}"
        switch (app.buildFrameworkType) {
            case HiveSpaceConstants.netCore:
                deployDotnetCore(project, app)
                break
            case HiveSpaceConstants.nodeJsSWA:
                deployStaticWebApp()
                break
            default:
                throw new Exception("No support for this type: ${app.buildFrameworkType}")
        }
    }
}

void deployDotnetCore(HiveSpaceProject project, HiveSpaceApp app) {
    stage('Build & Push All Apps') {
            withCredentials([usernamePassword(
                            credentialsId: project.credentialsId,
                            usernameVariable: 'DOCKER_USERNAME',
                            passwordVariable: 'DOCKER_PASSWORD'
                        )]) {
                sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin"

                def imageTag = app.getFullImageTag("${env.BUILD_NUMBER}")
                echo "🔧 Building image for ${app.name} at ${app.buildContext}"
            dir('HiveSpace') {
                sh "docker build -t ${imageTag} -f ${app.buildContext}"
                sh "docker push ${imageTag}"
            }
        }
    }
}
