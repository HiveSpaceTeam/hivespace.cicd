package hivespace.entity

class HiveSpaceProject implements Serializable {

    String name
    String gitRepo
    String branch
    String credentialsId
    String helmRepo
    List<HiveSpaceApp> apps = []

    HiveSpaceProject(Map args = [:]) {
        this.name = args.name ?: 'UnnamedProject'
        this.gitRepo = args.gitRepo ?: ''
        this.helmRepo = args.helmRepo ?: ''
        this.branch = args.branch ?: 'main'
        this.credentialsId = args.credentialsId ?: 'dockerhub-credentials'
        this.apps = args.apps ?: []
    }

    // void buildAllApps() {
    //     println "Starting build for project: ${name}"
    //     apps.each { app ->
    //         println "Building app: ${app.name}"
    //         app.build()  // Gọi hàm build của từng app
    //     }
    // }

    String toString() {
        return """\
HiveSpaceProject(
  name: ${name},
  gitRepo: ${gitRepo},
  branch: ${branch},
  credentialsId: ${credentialsId},
  apps: ${apps}
)
""".stripIndent()
    }

}
