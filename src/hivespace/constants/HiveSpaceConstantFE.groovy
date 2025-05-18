package hivespace.constants

import hivespace.entity.*

public class HiveSpaceConstantFE {
    
    static HiveSpaceProject project = new HiveSpaceProject(
        name: 'HiveSpace frontend',
        gitRepo: 'https://github.com/HiveSpaceTeam/hivespace.frontend',
        credentialsId: 'dockerhub-credentials',
        apps: [
            new HiveSpaceApp(name: 'frontend', dockerImage: 'dblmint/hivespace-frontend', buildFrameworkType: HiveSpaceConstants.nodeJsSWA),
        ]
    )

}

