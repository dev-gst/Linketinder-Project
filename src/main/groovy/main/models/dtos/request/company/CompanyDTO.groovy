package main.models.dtos.request.company

import main.models.dtos.request.login.LoginDetailsDTO
import main.util.exception.ParamValidation

class CompanyDTO {
    private final String name
    private final LoginDetailsDTO loginDetails
    private final String description
    private final String cnpj

    CompanyDTO(String name, LoginDetailsDTO loginDetailsDTO, String description, String cnpj) {
        ParamValidation.requireNonBlank(name, "Name cannot be null")
        ParamValidation.requireNonNull(loginDetailsDTO, "LoginDetails cannot be null")
        ParamValidation.requireNonBlank(description, "Description cannot be null")
        ParamValidation.requireNonBlank(cnpj, "CNPJ cannot be null")

        this.name = name
        this.loginDetails = loginDetailsDTO
        this.description = description
        this.cnpj = cnpj
    }

    String getName() {
        return name
    }

    LoginDetailsDTO getLoginDetails() {
        return loginDetails
    }

    String getDescription() {
        return description
    }

    String getCnpj() {
        return cnpj
    }
}
