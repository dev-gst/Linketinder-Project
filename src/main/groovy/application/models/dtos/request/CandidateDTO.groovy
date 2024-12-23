package application.models.dtos.request

import application.models.dtos.request.login.LoginDetailsDTO
import application.utils.exceptions.FieldNotSetException
import application.utils.validation.ParamValidation
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class CandidateDTO {

    final String firstName
    final String lastName
    final LoginDetailsDTO loginDetailsDTO
    final String cpf
    final Instant birthDate
    final String description
    final String education
    final Integer addressId

    @JsonCreator
    private CandidateDTO(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("loginDetails") LoginDetailsDTO loginDetailsDTO,
            @JsonProperty("cpf") String cpf,
            @JsonProperty("birthDate") Instant birthDate,
            @JsonProperty("description") String description,
            @JsonProperty("education") String education,
            @JsonProperty("addressId") int addressId) {
        this.firstName = firstName
        this.lastName = lastName
        this.loginDetailsDTO = loginDetailsDTO
        this.cpf = cpf
        this.birthDate = birthDate
        this.description = description
        this.education = education
        this.addressId = addressId
    }

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

    String getFirstName() {
        return firstName
    }

    String getLastName() {
        return lastName
    }

    LoginDetailsDTO getLoginDetailsDTO() {
        return loginDetailsDTO
    }

    String getCpf() {
        return cpf
    }

    Instant getBirthDate() {
        return birthDate
    }

    String getDescription() {
        return description
    }

    String getEducation() {
        return education
    }

    Integer getAddressId() {
        return addressId
    }

    static CandidateDTO of(Map<String, String> candidateInfo) {
        return new Builder()
                .firstName(candidateInfo.get("firstName"))
                .lastName(candidateInfo.get("lastName"))
                .loginDetailsDTO(new LoginDetailsDTO(candidateInfo.get("email"), candidateInfo.get("password")))
                .cpf(candidateInfo.get("cpf"))
                .birthDate(LocalDate.parse(candidateInfo.get("birthDate")))
                .description(candidateInfo.get("description"))
                .education(candidateInfo.get("education"))
                .addressId(Integer.parseInt(candidateInfo.get("addressId")))
                .build()
    }

    static class Builder {
        String firstName
        String lastName
        LoginDetailsDTO loginDetailsDTO
        String cpf
        Instant birthDate
        String description
        String education
        Integer addressId

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

        Builder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
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

        Builder addressId(Integer addressId) {
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
