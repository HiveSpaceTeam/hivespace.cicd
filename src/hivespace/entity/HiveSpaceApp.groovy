
package hivespace.entity

class HiveSpaceApp implements Serializable {

    String name
    String dockerImage
    String buildContext

    HiveSpaceApp(Map args = [:]) {
        this.name = args.name
        this.dockerImage = args.dockerImage
        this.buildContext = args.buildContext ?: '.'
    }

    String getFullImageTag(String tag = 'latest') {
        return "${dockerImage}:${tag}"
    }

    String toString() {
        return "App(name: ${name}, dockerImage: ${dockerImage}, context: ${buildContext})"
    }

}
