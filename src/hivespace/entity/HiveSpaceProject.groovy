package hivespace.entity

class HiveSpaceProject implements Serializable {

    String name
    String gitRepo
    String branch
    String credentialsId
    List<HiveSpaceApp> apps = []

    HiveSpaceProject(Map args = [:]) {
        this.name = args.name ?: 'UnnamedProject'
        this.gitRepo = args.gitRepo ?: ''
        this.branch = args.branch ?: 'main'
        this.credentialsId = args.credentialsId ?: 'dockerhub-credentials'
        this.apps = args.apps ?: []
    }

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
