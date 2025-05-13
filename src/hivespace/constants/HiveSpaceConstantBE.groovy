package hivespace.constants

import hivespace.entity.*

public class HiveSpaceConstantBE {
    
    static HiveSpaceProject project = new HiveSpaceProject(
        name: 'HiveSpace backend',
        gitRepo: 'https://github.com/HiveSpaceTeam/hivespace.backend',
        branch: 'production.g1',
        credentialsId: 'dockerhub-credentials',
        apps: [
            new HiveSpaceApp(name: 'hivespaceapp', dockerImage: 'dblmint/hivespace-hivespaceapp'),
        ]
    )

}

