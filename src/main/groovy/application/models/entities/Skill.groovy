package application.models.entities

import application.utils.exceptions.FieldNotSetException
import application.utils.validation.ParamValidation

class Skill {
    int id
    String name

    private Skill(Builder builder) {
        this.id = builder.id
        this.name = builder.name
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        Skill skill = (Skill) o

        if (id != skill.id) return false
        if (name != skill.name) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = id
        result = 31 * result + (name != null ? name.hashCode() : 0)
        return result
    }

    @Override
    String toString() {
        return name
    }

    static class Builder {
        int id
        String name

        boolean idSet = false
        boolean nameSet = false

        Builder id(int id) {
            ParamValidation.requirePositive(id, "ID needs to be positive")

            this.id = id
            idSet = true

            return this
        }

        Builder name(String name) {
            ParamValidation.requireNonBlank(name, "Name cannot be blank")

            this.name = name
            nameSet = true

            return this
        }

        Skill build() {
            validateFields()

            return new Skill(this)
        }

        private void validateFields() {
            if (!idSet) throw new FieldNotSetException("ID is not set")
            if (!nameSet) throw new FieldNotSetException("Name is not set")
        }
    }
}
