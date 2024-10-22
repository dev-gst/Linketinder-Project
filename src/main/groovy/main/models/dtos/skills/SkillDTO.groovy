package main.models.dtos.skills

import main.util.exception.ParamValidation

class SkillDTO {
    private final String name

    SkillDTO(String name) {
        ParamValidation.requireNonNull(name, "Name cannot be null")

        this.name = name
    }

    String getName() {
        return name
    }
}
