package main.models.dtos.request

import main.util.exception.ParamValidation
import main.util.exception.custom.FieldNotSetException

class JobOpeningDTO {

    final String name
    final String description
    final boolean isOpen
    final boolean isRemote
    final int companyId
    final Optional<Integer> addressId

    private JobOpeningDTO(Builder builder) {
        id = builder.id
        name = builder.name
        description = builder.description
        isOpen = builder.isOpen
        isRemote = builder.isRemote
        companyId = builder.companyId
        addressId = builder.addressId
    }

    String getName() {
        return name
    }

    String getDescription() {
        return description
    }

    boolean getIsOpen() {
        return isOpen
    }

    boolean getIsRemote() {
        return isRemote
    }

    int getCompanyId() {
        return companyId
    }

    Optional<Integer> getAddressId() {
        return addressId
    }

    static class Builder {

        String name
        String description
        boolean isOpen
        boolean isRemote
        int companyId
        Optional<Integer> addressId = Optional.empty()

        boolean setName = false
        boolean setDescription = false
        boolean setIsOpen = false
        boolean setIsRemote = false
        boolean setCompanyId = false
        boolean setAddressId = false

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

        JobOpeningDTO build() {
            validateFields()

            return new JobOpeningDTO(this)
        }

        private void validateFields() {
            if (!setName) throw new FieldNotSetException("name was not set")
            if (!setDescription) throw new FieldNotSetException("description was not set")
            if (!setIsOpen) throw new FieldNotSetException("isOpen was not set")
            if (!setIsRemote) throw new FieldNotSetException("isRemote was not set")
            if (!setCompanyId) throw new FieldNotSetException("companyId was not set")
        }
    }
}