package application.models.dtos.request

import application.utils.exceptions.FieldNotSetException
import application.utils.validation.ParamValidation
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class SkillDTO {

    final String name

    @JsonCreator
    private SkillDTO(@JsonProperty("name") String name) {
        ParamValidation.requireNonBlank(name, "Name cannot be blank")

        this.name = name
    }

    private SkillDTO(Builder builder) {
        this.name = builder.name
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

            return new SkillDTO(this)
        }

        private void validate() {
            if (!nameSet) throw new FieldNotSetException("Name is required")
        }
    }
}
