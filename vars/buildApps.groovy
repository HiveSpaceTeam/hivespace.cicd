import hivespace.entity.*
import hivespace.constants.HiveSpaceConstants

def call(HiveSpaceProject project) {
    echo 'build apps  buildFrameworkType: ${app.buildFrameworkType} - ${HiveSpaceConstants.netCore} - ${HiveSpaceConstants.nodeJsSWA}'
    project.apps.each { app ->
        switch (app.buildFrameworkType) {
            case HiveSpaceConstants.netCore:
                deployDotnetCore(project, app)
                break
            case HiveSpaceConstants.nodeJsSWA:
                deployStaticWebApp(project, app)
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

                def imageTag = app.getFullImageTag(tag)
                echo "🔧 Building image for ${app.name} at ${app.buildContext}"
                sh "docker build -t ${imageTag} ${app.buildContext}"
                sh "docker push ${imageTag}"
        }
    }
}
