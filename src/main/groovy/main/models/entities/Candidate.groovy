package main.models.entities

import main.models.entities.login.LoginDetails

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class Candidate {
    int id
    String firstName
    String lastName
    LoginDetails loginDetails
    String description
    Instant birthDate
    String cpf
    String education
    int addressId

    private Candidate(Builder builder) {
        this.id = builder.id
        this.firstName = builder.firstName
        this.lastName = builder.lastName
        this.loginDetails = builder.loginDetails
        this.description = builder.description
        this.birthDate = builder.birthDate
        this.cpf = builder.cpf
        this.education = builder.education
        this.addressId = builder.addressId
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        Candidate candidate = (Candidate) o

        if (addressId != candidate.addressId) return false
        if (id != candidate.id) return false
        if (birthDate != candidate.birthDate) return false
        if (cpf != candidate.cpf) return false
        if (education != candidate.education) return false
        if (firstName != candidate.firstName) return false
        if (lastName != candidate.lastName) return false
        if (loginDetails != candidate.loginDetails) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = id
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0)
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0)
        result = 31 * result + (loginDetails != null ? loginDetails.hashCode() : 0)
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0)
        result = 31 * result + (cpf != null ? cpf.hashCode() : 0)
        result = 31 * result + (education != null ? education.hashCode() : 0)
        result = 31 * result + addressId
        return result
    }

    static class Builder {
        int id
        String firstName
        String lastName
        LoginDetails loginDetails
        String description
        Instant birthDate
        String cpf
        String education
        int addressId

        private boolean idSet = false
        private boolean firstNameSet = false
        private boolean lastNameSet = false
        private boolean loginDetailsSet = false
        private boolean descriptionSet = false
        private boolean birthDateSet = false
        private boolean cpfSet = false
        private boolean educationSet = false
        private boolean addressIdSet = false

        Builder id(int id) {
            this.id = id
            this.idSet = true

            return this
        }

        Builder firstName(String firstName) {
            this.firstName = firstName
            this.firstNameSet = true

            return this
        }

        Builder lastName(String lastName) {
            this.lastName = lastName
            this.lastNameSet = true

            return this
        }

        Builder loginDetails(LoginDetails loginDetails) {
            this.loginDetails = loginDetails
            this.loginDetailsSet = true

            return this
        }

        Builder description(String description) {
            this.description = description
            this.descriptionSet = true

            return this
        }

        Builder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
            this.birthDateSet = true

            return this
        }

        Builder cpf(String cpf) {
            this.cpf = cpf
            this.cpfSet = true

            return this
        }

        Builder education(String education) {
            this.education = education
            this.educationSet = true

            return this
        }

        Builder addressId(int addressId) {
            this.addressId = addressId
            this.addressIdSet = true

            return this
        }

        Candidate build() {
            validateFields()

            return new Candidate(this)
        }

        void validateFields() {
            if (!firstNameSet) throw new IllegalStateException("First name must be set")
            if (!lastNameSet) throw new IllegalStateException("Last name must be set")
            if (!loginDetails) throw new IllegalStateException("Login details must be set")
            if (!descriptionSet) throw new IllegalStateException("Description must be set")
            if (!birthDateSet) throw new IllegalStateException("Birth date must be set")
            if (!cpfSet) throw new IllegalStateException("CPF must be set")
            if (!educationSet) throw new IllegalStateException("Education must be set")
            if (!addressIdSet) throw new IllegalStateException("Address ID must be set")
        }
    }
}
