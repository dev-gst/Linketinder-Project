package main.models.dtos.request

import main.util.exception.ParamValidation
import main.util.exception.custom.FieldNotSetException

class SkillDTO {
    final String name

    SkillDTO(String name) {
        ParamValidation.requireNonBlank(name, "Name cannot be blank")

        this.name = name
    }

    String getName() {
        return name
    }

    static SkillDTO of(String name) {
        return new SkillDTO(name)
    }

    static class Builder {
        String name

        boolean nameSet = false

        Builder name(String name) {
            ParamValidation.requireNonBlank(name, "Name cannot be blank")

            this.name = name
            nameSet = true

            return this
        }

        SkillDTO build() {
            validate()

            return new SkillDTO(name)
        }

        private void validate() {
            if (!nameSet) throw new FieldNotSetException("Name is required")
        }
    }
}
