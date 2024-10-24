package main.models.entities.skill

import main.util.exception.ParamValidation

class Skill {
    int id
    String name

    Skill(int id, String name) {
        ParamValidation.requirePositive(id, "ID needs to be positive")
        ParamValidation.requireNonBlank(name, "Name cannot be blank")

        this.id = id
        this.name = name
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
}
