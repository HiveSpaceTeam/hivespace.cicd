import hivespace.entity.*
import hivespace.constants.HiveSpaceConstants

def call() {
    def projects = HiveSpaceConstants.allProjects
    HiveSpaceProject selectProject = projects.findAll { i -> i.name == params.PROJECT_NAME }[0]
    deployApp(selectProject)
}
