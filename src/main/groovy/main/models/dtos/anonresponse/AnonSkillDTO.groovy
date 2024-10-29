package main.models.dtos.anonresponse

import main.util.exception.ParamValidation

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
