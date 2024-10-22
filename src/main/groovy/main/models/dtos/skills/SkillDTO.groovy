package main.models.dtos.skills

import main.util.exception.ParamValidation

class SkillDTO {
    private final String name

    SkillDTO(String name) {
        ParamValidation.requireNonBlank(name, "Name cannot be blank")

        this.name = name
    }

    String getName() {
        return name
    }
}
