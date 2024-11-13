package application.models.dtos.request

import application.models.dtos.request.login.LoginDetailsDTO
import application.utils.exceptions.FieldNotSetException
import application.utils.validation.ParamValidation

class CompanyDTO {

    final String name
    final LoginDetailsDTO loginDetailsDTO
    final String description
    final String cnpj
    final int addressId

    private CompanyDTO(Builder builder) {
        name = builder.name
        loginDetailsDTO = builder.loginDetailsDTO
        description = builder.description
        cnpj = builder.cnpj
        addressId = builder.addressId
    }

    String getName() {
        return name
    }

    LoginDetailsDTO getLoginDetailsDTO() {
        return loginDetailsDTO
    }

    String getDescription() {
        return description
    }

    String getCnpj() {
        return cnpj
    }

    int getAddressId() {
        return addressId
    }

    static class Builder {

        String name
        LoginDetailsDTO loginDetailsDTO
        String description
        String cnpj
        int addressId

        boolean nameSet = false
        boolean loginDetailsDTOSet = false
        boolean descriptionSet = false
        boolean cnpjSet = false
        boolean addressIdSet = false

        Builder setName(String name) {
            ParamValidation.requireNonBlank(name, "Name cannot be null or blank")

            this.name = name
            nameSet = true

            return this
        }

        Builder setLoginDetailsDTO(LoginDetailsDTO loginDetailsDTO) {
            ParamValidation.requireNonNull(loginDetailsDTO, "Login details cannot be null")

            this.loginDetailsDTO = loginDetailsDTO
            loginDetailsDTOSet = true

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
            if (!loginDetailsDTOSet) throw new FieldNotSetException("Login details must be set")
            if (!descriptionSet) throw new FieldNotSetException("Description must be set")
            if (!cnpjSet) throw new FieldNotSetException("CNPJ must be set")
            if (!addressIdSet) throw new FieldNotSetException("Address ID must be set")
        }
    }
}
