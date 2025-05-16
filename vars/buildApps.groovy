import hivespace.entity.*

def call(HiveSpaceProject project, String branch) {
    println "build apps"
    project.apps.each { app ->
        // app.build()
        println "Building app: ${app.name}"
    }
}
