package Linketinder.models.entities

class JobOpeningSkill {
    int id
    Skill skill
    JobOpening jobOpening

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        JobOpeningSkill that = (JobOpeningSkill) o

        if (id != that.id) return false
        if (skill != that.skill) return false
        if (jobOpening != that.jobOpening) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = id
        result = 31 * result + (skill != null ? skill.hashCode() : 0)
        result = 31 * result + (jobOpening != null ? jobOpening.hashCode() : 0)

        return result
    }
}