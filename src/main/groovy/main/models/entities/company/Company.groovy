package main.models.entities.company


import main.models.entities.login.LoginDetails
import main.util.exception.ParamValidation

class Company {
    int id
    CompanyDetails companyDetails
    LoginDetails loginDetails

    Company(int id, CompanyDetails companyDetails, LoginDetails loginDetails) {
        ParamValidation.requirePositive(id, "Id cannot be negative")
        ParamValidation.requireNonNull(companyDetails, "CompanyDetails cannot be null")
        ParamValidation.requireNonNull(loginDetails, "LoginDetails cannot be null")

        this.id = id
        this.companyDetails = companyDetails
        this.loginDetails = loginDetails
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        Company company = (Company) o

        if (id != company.id) return false
        if (companyDetails != company.companyDetails) return false
        if (jobOpenings != company.jobOpenings) return false
        if (loginDetails != company.loginDetails) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = id
        result = 31 * result + (companyDetails != null ? companyDetails.hashCode() : 0)
        result = 31 * result + (loginDetails != null ? loginDetails.hashCode() : 0)
        result = 31 * result + (jobOpenings != null ? jobOpenings.hashCode() : 0)
        return result
    }
}
