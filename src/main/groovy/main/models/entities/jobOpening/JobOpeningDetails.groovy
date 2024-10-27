package main.models.entities.jobOpening

import main.util.exception.ParamValidation

class JobOpeningDetails {

    String name
    String description
    boolean isOpen
    JobOpeningAddress jobOpeningAddress

    JobOpeningDetails(String name, String description, boolean isOpen, JobOpeningAddress jobOpeningAddress) {
        ParamValidation.requireNonBlank(name, "Name cannot be null or blank")
        ParamValidation.requireNonBlank(description, "Description cannot be null or blank")
        ParamValidation.requireNonNull(jobOpeningAddress, "JobOpeningAddress cannot be null")

        this.name = name
        this.description = description
        this.isOpen = isOpen
        this.jobOpeningAddress = jobOpeningAddress
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        JobOpeningDetails that = (JobOpeningDetails) o

        if (isOpen != that.isOpen) return false
        if (description != that.description) return false
        if (jobOpeningAddress != that.jobOpeningAddress) return false
        if (name != that.name) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (isOpen ? 1 : 0)
        result = 31 * result + (jobOpeningAddress != null ? jobOpeningAddress.hashCode() : 0)
        return result
    }
}
