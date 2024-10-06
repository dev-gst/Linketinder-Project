package Linketinder.models.entities

class CandidateSkill {
    int id
    Skill skill
    Candidate candidate

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        CandidateSkill that = (CandidateSkill) o

        if (id != that.id) return false
        if (skill != that.skill) return false
        if (candidate != that.candidate) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = id
        result = 31 * result + (skill != null ? skill.hashCode() : 0)
        result = 31 * result + (candidate != null ? candidate.hashCode() : 0)

        return result
    }
}

