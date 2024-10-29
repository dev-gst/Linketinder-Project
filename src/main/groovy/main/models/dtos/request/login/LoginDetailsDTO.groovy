package main.models.dtos.request.login

import main.util.exception.ParamValidation

class LoginDetailsDTO {

    private final String email
    private final String password

    LoginDetailsDTO(String email, String password) {
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
