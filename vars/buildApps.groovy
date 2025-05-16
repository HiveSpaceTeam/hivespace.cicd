import hivespace.entity.*

def call(HiveSpaceProject project) {
    stage('BuildApps') {
        echo 'build apps'
        project.apps.each { app ->
            // app.build()
            echo "Building app: ${app.name}"
        }
    }
   
}
