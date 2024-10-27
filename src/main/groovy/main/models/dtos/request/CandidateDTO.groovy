package main.models.dtos.request

import main.models.dtos.request.login.LoginDetailsDTO
import main.util.exception.ParamValidation
import main.util.exception.custom.FieldNotSetException

import java.time.Instant

class CandidateDTO {

    final String firstName
    final String lastName
    final LoginDetailsDTO loginDetailsDTO
    final String cpf
    final Instant birthDate
    final String description
    final String education
    final int addressId

    private CandidateDTO(Builder builder) {
        this.firstName = builder.firstName
        this.lastName = builder.lastName
        this.loginDetailsDTO = builder.loginDetailsDTO
        this.cpf = builder.cpf
        this.birthDate = builder.birthDate
        this.description = builder.description
        this.education = builder.education
        this.addressId = builder.addressId
    }

    static class Builder {
        String firstName
        String lastName
        LoginDetailsDTO loginDetailsDTO
        String cpf
        Instant birthDate
        String description
        String education
        int addressId

        private boolean firstNameSet = false
        private boolean lastNameSet = false
        private boolean loginDetailsDTOSet = false
        private boolean cpfSet = false
        private boolean birthDateSet = false
        private boolean descriptionSet = false
        private boolean educationSet = false
        private boolean addressIdSet = false

        Builder firstName(String firstName) {
            ParamValidation.requireNonBlank(firstName, "First name cannot be null or blank")

            this.firstName = firstName
            this.firstNameSet = true

            return this
        }

        Builder lastName(String lastName) {
            ParamValidation.requireNonBlank(lastName, "Last name cannot be null or blank")

            this.lastName = lastName
            this.lastNameSet = true

            return this
        }

        Builder loginDetailsDTO(LoginDetailsDTO loginDetailsDTO) {
            ParamValidation.requireNonNull(loginDetailsDTO, "Login details cannot be null")

            this.loginDetailsDTO = loginDetailsDTO
            this.loginDetailsDTOSet = true

            return this
        }

        Builder cpf(String cpf) {
            ParamValidation.requireNonBlank(cpf, "CPF cannot be null or blank")

            this.cpf = cpf
            this.cpfSet = true

            return this
        }

        Builder birthDate(Instant birthDate) {
            ParamValidation.requireNonNull(birthDate, "Birth date cannot be null")

            this.birthDate = birthDate
            this.birthDateSet = true

            return this
        }

        Builder description(String description) {
            ParamValidation.requireNonBlank(description, "Description cannot be null or blank")

            this.description = description
            this.descriptionSet = true

            return this
        }

        Builder education(String education) {
            ParamValidation.requireNonBlank(education, "Education cannot be null or blank")

            this.education = education
            this.educationSet = true

            return this
        }

        Builder addressId(int addressId) {
            ParamValidation.requirePositive(addressId, "Address ID must be positive")

            this.addressId = addressId
            this.addressIdSet = true

            return this
        }

        CandidateDTO build() {
            validateFields()

            return new CandidateDTO(this)
        }

        private void validateFields() {
            if (!firstNameSet) throw new FieldNotSetException("First name must be set")
            if (!lastNameSet) throw new FieldNotSetException("Last name must be set")
            if (!loginDetailsDTOSet) throw new FieldNotSetException("Login details must be set")
            if (!cpfSet) throw new FieldNotSetException("CPF must be set")
            if (!birthDateSet) throw new FieldNotSetException("Birth date must be set")
            if (!descriptionSet) throw new FieldNotSetException("Description must be set")
            if (!educationSet) throw new FieldNotSetException("Education must be set")
            if (!addressIdSet) throw new FieldNotSetException("Address ID must be set")
        }
    }
}
