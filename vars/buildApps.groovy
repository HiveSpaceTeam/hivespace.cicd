import hivespace.entity.*
import hivespace.constants.HiveSpaceConstants

def call(HiveSpaceProject project) {
    echo 'build apps'
    project.apps.each { app ->
        switch (app.buildFrameworkType) {
            case HiveSpaceConstants.netCore:
                deployDotnetCore()
                break
            case HiveSpaceConstants.nodeJsSWA:
                deployStaticWebApp(app)
                break
            default:
                throw new Exception('No support for this type')
        }
    }
}

void deployDotnetCore(HiveSpaceApp app) {
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
