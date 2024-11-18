package application.models.dtos.request.login


import application.utils.validation.ParamValidation
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class LoginDetailsDTO {

    private final String email
    private final String password

    @JsonCreator
    LoginDetailsDTO(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password) {
        ParamValidation.requireNonBlank(email, "email cannot be null or blank")
        ParamValidation.requireNonBlank(password, "Password cannot be null or blank")

        this.email = email
        this.password = password
    }

    String getEmail() {
        return email
    }

    String getPassword() {
        return password
    }
}
