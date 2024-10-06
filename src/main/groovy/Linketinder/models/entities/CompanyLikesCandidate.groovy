package Linketinder.models.entities

class CompanyLikesCandidate {
    int id
    Company company
    Candidate candidate
    JobOpening jobOpening
    Date likedAt

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        CompanyLikesCandidate that = (CompanyLikesCandidate) o

        if (id != that.id) return false
        if (company != that.company) return false
        if (candidate != that.candidate) return false
        if (jobOpening != that.jobOpening) return false
        if (likedAt != that.likedAt) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = id
        result = 31 * result + (company != null ? company.hashCode() : 0)
        result = 31 * result + (candidate != null ? candidate.hashCode() : 0)
        result = 31 * result + (jobOpening != null ? jobOpening.hashCode() : 0)
        result = 31 * result + (likedAt != null ? likedAt.hashCode() : 0)

        return result
    }
}