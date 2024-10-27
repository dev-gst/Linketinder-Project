package main.models.entities.jobOpening

import main.util.exception.ParamValidation

class JobOpeningAddress {

    Integer addressId
    boolean isRemote

    JobOpeningAddress(Integer addressId, boolean isRemote) {
        if (!isRemote) {
            ParamValidation.requirePositive(addressId, "addressId should be positive if job is not remote")
        }

        this.addressId = addressId
        this.isRemote = isRemote
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        JobOpeningAddress that = (JobOpeningAddress) o

        if (isRemote != that.isRemote) return false
        if (addressId != that.addressId) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = (addressId != null ? addressId.hashCode() : 0)
        result = 31 * result + (isRemote ? 1 : 0)
        return result
    }
}
