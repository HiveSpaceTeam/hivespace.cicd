import groovy.json.JsonOutput
import hivespace.constants.HiveSpaceConstants

def call() {
    def projects = HiveSpaceConstants.allProjects
    def projectNames = projects*.name

    // Tạo Map từ tên project => danh sách image
    def projectImageMap = [:]
    for (project in projects) {
        projectImageMap[project.name] = project.apps*.name // hoặc *.imageName nếu là image
    }

    // Tạo script chuỗi động cho CascadeChoice
    def imageScript = """
        import groovy.json.JsonSlurper
        def project = binding.getVariable("PROJECT_NAME")
        def projectImageMap = new JsonSlurper().parseText('${JsonOutput.toJson(projectImageMap)}')
        return projectImageMap[project] ?: ["Không có image nào 123a"]
    """

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
                    fallbackScript: [
                        classpath: [],
                        sandbox: true,
                        script: 'return ["Lỗi - Không có image nào"]'
                    ],
                    script: [
                        classpath: [],
                        sandbox: true,
                        script: imageScript
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
