package main.models.entities.jobOpening

import main.util.exception.ParamValidation

class JobOpening {

    int id
    JobOpeningDetails jobOpeningDetails
    int companyId

    JobOpening(int id, JobOpeningDetails jobOpeningDetails, int companyId) {
        ParamValidation.requirePositive(id, "id must be positive")
        ParamValidation.requireNonNull(jobOpeningDetails, "JobOpeningDetails cannot be null")
        ParamValidation.requirePositive(companyId, "companyId must be positive")

        this.id = id
        this.jobOpeningDetails = jobOpeningDetails
        this.companyId = companyId
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        JobOpening that = (JobOpening) o

        if (companyId != that.companyId) return false
        if (id != that.id) return false
        if (jobOpeningDetails != that.jobOpeningDetails) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = id
        result = 31 * result + (jobOpeningDetails != null ? jobOpeningDetails.hashCode() : 0)
        result = 31 * result + companyId
        return result
    }
}
