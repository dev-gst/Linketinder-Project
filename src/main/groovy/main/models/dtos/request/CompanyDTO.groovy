package main.models.dtos.request

import main.models.dtos.request.login.LoginDetailsDTO
import main.util.exception.ParamValidation
import main.util.exception.custom.FieldNotSetException

class CompanyDTO {

    final String name
    final LoginDetailsDTO loginDetails
    final String description
    final String cnpj
    final int addressId

    private CompanyDTO(Builder builder) {
        name = builder.name
        loginDetails = builder.loginDetails
        description = builder.description
        cnpj = builder.cnpj
        addressId = builder.addressId
    }

    static class Builder {

        String name
        LoginDetailsDTO loginDetails
        String description
        String cnpj
        int addressId

        boolean nameSet = false
        boolean loginDetailsSet = false
        boolean descriptionSet = false
        boolean cnpjSet = false
        boolean addressIdSet = false

        Builder setName(String name) {
            ParamValidation.requireNonBlank(name, "Name cannot be null or blank")

            this.name = name
            nameSet = true

            return this
        }

        Builder setLoginDetails(LoginDetailsDTO loginDetails) {
            ParamValidation.requireNonNull(loginDetails, "Login details cannot be null")

            this.loginDetails = loginDetails
            loginDetailsSet = true

            return this
        }

        Builder setDescription(String description) {
            ParamValidation.requireNonBlank(description, "Description cannot be null or blank")

            this.description = description
            descriptionSet = true

            return this
        }

        Builder setCnpj(String cnpj) {
            ParamValidation.requireNonBlank(cnpj, "CNPJ cannot be null or blank")

            this.cnpj = cnpj
            cnpjSet = true

            return this
        }

        Builder setAddressId(int addressId) {
            ParamValidation.requirePositive(addressId, "Address ID cannot be negative")

            this.addressId = addressId
            addressIdSet = true

            return this
        }

        CompanyDTO build() {
            validate()

            return new CompanyDTO(this)
        }

        private void validate() {
            if (!nameSet) throw new FieldNotSetException("Name must be set")
            if (!loginDetailsSet) throw new FieldNotSetException("Login details must be set")
            if (!descriptionSet) throw new FieldNotSetException("Description must be set")
            if (!cnpjSet) throw new FieldNotSetException("CNPJ must be set")
            if (!addressIdSet) throw new FieldNotSetException("Address ID must be set")
        }
    }
}
