package main.models.entities

class Skill {
    int id
    String name

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
