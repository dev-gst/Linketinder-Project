package main.models.entities

import main.util.exception.ParamValidation
import main.util.exception.custom.FieldNotSetException

class JobOpening {

    int id
    String name
    String description
    boolean isOpen
    boolean isRemote
    int companyId
    Optional<Integer> addressId

    private JobOpening(Builder builder) {
        id = builder.id
        name = builder.name
        description = builder.description
        isOpen = builder.isOpen
        isRemote = builder.isRemote
        companyId = builder.companyId
        addressId = builder.addressId
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        JobOpening that = (JobOpening) o

        if (companyId != that.companyId) return false
        if (id != that.id) return false
        if (isOpen != that.isOpen) return false
        if (isRemote != that.isRemote) return false
        if (addressId != that.addressId) return false
        if (description != that.description) return false
        if (name != that.name) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = id
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (isOpen ? 1 : 0)
        result = 31 * result + (isRemote ? 1 : 0)
        result = 31 * result + companyId
        result = 31 * result + (addressId != null ? addressId.hashCode() : 0)
        return result
    }

    static class Builder {

        int id
        String name
        String description
        boolean isOpen
        boolean isRemote
        int companyId
        Optional<Integer> addressId = Optional.empty()

        boolean setId = false
        boolean setName = false
        boolean setDescription = false
        boolean setIsOpen = false
        boolean setIsRemote = false
        boolean setCompanyId = false
        boolean setAddressId = false

        Builder setId(int id) {
            ParamValidation.requirePositive(id, "id should be positive")

            this.id = id
            setId = true

            return this
        }

        Builder setName(String name) {
            ParamValidation.requireNonBlank(name, "Name cannot be null or blank")

            this.name = name
            setName = true

            return this
        }

        Builder setDescription(String description) {
            ParamValidation.requireNonBlank(description, "Description cannot be null or blank")

            this.description = description
            setDescription = true

            return this
        }

        Builder setIsOpen(boolean isOpen) {
            this.isOpen = isOpen
            setIsOpen = true

            return this
        }

        Builder setIsRemote(boolean isRemote) {
            this.isRemote = isRemote
            setIsRemote = true

            return this
        }

        Builder setCompanyId(int companyId) {
            ParamValidation.requirePositive(companyId, "companyId should be positive")

            this.companyId = companyId
            setCompanyId = true

            return this
        }

        Builder setAddressId(Optional<Integer> addressId) {
            ParamValidation.requireNonNull(addressId, "optional was not initialized")

            this.addressId = addressId
            setAddressId = true

            return this
        }

        JobOpening build() {
            validateFields()

            return new JobOpening(this)
        }

        private void validateFields() {
            if (!setId) throw new FieldNotSetException("id was not set")
            if (!setName) throw new FieldNotSetException("name was not set")
            if (!setDescription) throw new FieldNotSetException("description was not set")
            if (!setIsOpen) throw new FieldNotSetException("isOpen was not set")
            if (!setIsRemote) throw new FieldNotSetException("isRemote was not set")
            if (!setCompanyId) throw new FieldNotSetException("companyId was not set")
        }
    }
}
