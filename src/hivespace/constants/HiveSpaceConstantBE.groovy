package hivespace.constants

import hivespace.entity.*

public class HiveSpaceConstantBE {

    static HiveSpaceProject project = new HiveSpaceProject(
        name: 'HiveSpace backend',
        gitRepo: 'https://github.com/HiveSpaceTeam/hivespace.backend',
        helmRepo: 'https://github.com/HiveSpaceTeam/hivespace.helm',
        credentialsId: 'dockerhub-credentials',
        apps: [
            new HiveSpaceApp(name: 'hivespaceapp', dockerImage: 'dblmint/hivespace-hivespaceapp', buildFrameworkType: 1, buildContext:"HiveSpace.Application/Dockerfile ."),
            // new HiveSpaceApp(name: 'hivespaceauth', dockerImage: 'dblmint/hivespace-hivespaceauth', buildFrameworkType: 1, buildContext:"HiveSpace.Application/Dockerfile ."),
        ]
    )

}

