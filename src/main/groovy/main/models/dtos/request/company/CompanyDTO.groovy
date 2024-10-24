package main.models.dtos.request.company

import main.models.dtos.request.login.LoginDetailsDTO
import main.util.exception.ParamValidation

class CompanyDTO {

    private final CompanyDetailsDTO companyDetailsDTO
    private final LoginDetailsDTO loginDetails

    CompanyDTO(CompanyDetailsDTO companyDetailsDTO, LoginDetailsDTO loginDetails) {
        ParamValidation.requireNonNull(companyDetailsDTO, "CompanyDetails cannot be null")
        ParamValidation.requireNonNull(loginDetails, "LoginDetails cannot be null")

        this.companyDetailsDTO = companyDetailsDTO
        this.loginDetails = loginDetails
    }

    CompanyDetailsDTO getCompanyDetailsDTO() {
        return companyDetailsDTO
    }

    LoginDetailsDTO getLoginDetails() {
        return loginDetails
    }
}
