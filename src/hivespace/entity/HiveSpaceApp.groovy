package hivespace.entity

class HiveSpaceApp implements Serializable {

    String name
    String dockerImage
    String buildContext

    /**
    * 1: net core
    * 2: nodejs - static web app
    */
    Integer buildFrameworkType

    HiveSpaceApp(Map args = [:]) {
        this.name = args.name
        this.dockerImage = args.dockerImage
        this.buildContext = args.buildContext ?: '.'
        this.buildFrameworkType = args.buildFrameworkType
    }

    String getFullImageTag(String tag = 'latest') {
        return "${dockerImage}:${tag}"
    }

    String toString() {
        return "App(name: ${name}, dockerImage: ${dockerImage}, context: ${buildContext})"
    }

}
