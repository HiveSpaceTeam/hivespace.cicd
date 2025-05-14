import hivespace.constants.HiveSpaceConstants

def call() {
    def projects = HiveSpaceConstants.allProjects
    def projectNames = projects*.name

    properties([
        parameters([
            choice(
                name: 'PROJECT_NAME',
                choices: projectNames,
                description: 'Chọn project cần build'
            ),
            [
                $class: 'CascadeChoiceParameter',
                choiceType: 'PT_MULTI_SELECT',
                description: 'Chọn image cần build (phụ thuộc vào project đã chọn)',
                filterLength: 1,
                filterable: true,
                name: 'IMAGE_NAME',
                randomName: 'param_image_name',
                referencedParameters: 'PROJECT_NAME',
                script: [
                    $class: 'GroovyScript',
                    fallbackScript: [classpath: [], sandbox: true, script: 'return ["Không có image nào"]'],
                    script: [
                        classpath: [],
                        sandbox: true,
                        script: '''
                            import hivespace.constants.*
                            def projectName =  binding.getVariable("PROJECT_NAME")
                            def project = HiveSpaceConstants.allProjects.find { it.name == projectName }
                            if (project == null) {
                                println "Không tìm thấy project tương ứng"
                                return ["Không tìm thấy project"]
                            }
                               println "Found project: \$project.name"
                                println "Apps: " + project.apps*.name
                            return project.apps*.name
                        '''
                    ]
                ]
            ],
            choice(
                name: 'ENVIRONMENT',
                choices: ['dev', 'staging', 'production'],
                description: 'Build code cho môi trường nào'
            ),
            string(
                name: 'BRANCH',
                defaultValue: '',
                description: 'Lấy code từ nhánh nào. Để trống tự động lấy từ tham số branch'
            )
        ])
    ])
}
