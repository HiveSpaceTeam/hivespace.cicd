import hivespace.entity.*
import hivespace.constants.HiveSpaceConstants

def call(HiveSpaceProject project, String branch) {
    project.apps.each { app ->
        echo "build apps  buildFrameworkType: ${app.buildFrameworkType} - ${HiveSpaceConstants.netCore} - ${HiveSpaceConstants.nodeJsSWA}"
        switch (app.buildFrameworkType) {
            case HiveSpaceConstants.netCore:
                deployDotnetCore(project, app, branch)
                break
            case HiveSpaceConstants.nodeJsSWA:
                deployStaticWebApp()
                break
            default:
                throw new Exception("No support for this type: ${app.buildFrameworkType}")
        }
    }
}

void deployDotnetCore(HiveSpaceProject project, HiveSpaceApp app, String branch) {
    stage('Build & Push Image') {
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
    stage('Sync Helm') {
            // Clean workspace and clone
            sh 'rm -rf helm-repo'
            sh "git clone -b ${branch} ${project.helmRepo} helm-repo"

        dir('helm-repo') {
                def tag = env.BUILD_NUMBER
                def filePath = sh(script: "find . -type f -name 'deployment.yaml' | grep templates", returnStdout: true).trim()
                sh """
                sed -i 's|image: \\(.*\\):.*|image: \\1:${tag}|' ${filePath}
                """
            withCredentials([usernamePassword(
                            credentialsId: 'nguyenphuctinh-github', //temp
                            usernameVariable: 'GIT_USER',
                            passwordVariable: 'GIT_PASS'
                        )]) {
                def securedHelmRepo = project.helmRepo.replace('https://', "https://${GIT_USER}:${GIT_PASS}@") + '.git'

                sh """
                    git config user.name "jenkins"
                    git config user.email "jenkins@yourcompany.com"
                    git remote set-url origin ${securedHelmRepo}
                    git add ${filePath}
                    git commit -m "Update image tag to ${env.BUILD_NUMBER}" || echo "Nothing to commit"
                    git push origin ${branch}
                """
            }
        }
    }
}
