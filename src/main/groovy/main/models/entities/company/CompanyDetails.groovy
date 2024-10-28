package main.models.entities.company

import main.models.entities.Address
import main.util.exception.ParamValidation

class CompanyDetails {

    String name
    String description
    String cnpj
    Address address

    CompanyDetails(String name, String description, String cnpj, Address address) {
        ParamValidation.requireNonBlank(name, "Name cannot be null or blank")
        ParamValidation.requireNonBlank(description, "Description cannot be null or blank")
        ParamValidation.requireNonBlank(cnpj, "CNPJ cannot be null or blank")
        ParamValidation.requireNonNull(address, "Address cannot be null")

        this.name = name
        this.description = description
        this.cnpj = cnpj
        this.address = address
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        CompanyDetails that = (CompanyDetails) o

        if (address != that.address) return false
        if (cnpj != that.cnpj) return false
        if (description != that.description) return false
        if (name != that.name) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (cnpj != null ? cnpj.hashCode() : 0)
        result = 31 * result + (address != null ? address.hashCode() : 0)
        return result
    }
}
