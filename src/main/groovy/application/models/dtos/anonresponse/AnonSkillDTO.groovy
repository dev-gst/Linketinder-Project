package application.models.dtos.anonresponse

import application.utils.validation.ParamValidation

class AnonSkillDTO {
    private final String name

    AnonSkillDTO(String name) {
        ParamValidation.requireNonBlank(name, "Name cannot be blank")

        this.name = name
    }

    String getName() {
        return name
    }
}
