package hivespace.constants

import hivespace.entity.*

public class HiveSpaceConstantBE {

    static HiveSpaceProject project = new HiveSpaceProject(
        name: 'HiveSpace backend',
        gitRepo: 'https://github.com/HiveSpaceTeam/hivespace.backend',
        credentialsId: 'dockerhub-credentials',
        apps: [
            new HiveSpaceApp(name: 'hivespaceapp', dockerImage: 'dblmint/hivespace-hivespaceapp', buildFrameworkType: HiveSpaceConstants.netCore),
            new HiveSpaceApp(name: 'hivespaceauth', dockerImage: 'dblmint/hivespace-hivespaceauth', buildFrameworkType: HiveSpaceConstants.netCore),
        ]
    )

}

