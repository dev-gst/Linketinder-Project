package application.models.entities

import application.models.entities.login.LoginDetails
import application.utils.exceptions.FieldNotSetException
import application.utils.validation.ParamValidation

class Company {

    int id
    String name
    LoginDetails loginDetails
    String description
    String cnpj
    int addressId

    private Company(Builder builder) {
        id = builder.id
        name = builder.name
        loginDetails = builder.loginDetails
        description = builder.description
        cnpj = builder.cnpj
        addressId = builder.addressId
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        Company company = (Company) o

        if (addressId != company.addressId) return false
        if (id != company.id) return false
        if (cnpj != company.cnpj) return false
        if (description != company.description) return false
        if (loginDetails != company.loginDetails) return false
        if (name != company.name) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = id
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (loginDetails != null ? loginDetails.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (cnpj != null ? cnpj.hashCode() : 0)
        result = 31 * result + addressId
        return result
    }

    static class Builder {

        int id
        LoginDetails loginDetails
        String name
        String description
        String cnpj
        int addressId

        boolean idSet = false
        boolean loginDetailsSet = false
        boolean nameSet = false
        boolean descriptionSet = false
        boolean cnpjSet = false
        boolean addressIdSet = false

        Builder setId(int id) {
            ParamValidation.requirePositive(id, "ID cannot be negative")

            this.id = id
            idSet = true

            return this
        }

        Builder setName(String name) {
            ParamValidation.requireNonBlank(name, "Name cannot be null or blank")

            this.name = name
            nameSet = true

            return this
        }

        Builder setLoginDetails(LoginDetails loginDetails) {
            ParamValidation.requireNonNull(loginDetails, "Login details cannot be null")

            this.loginDetails = loginDetails
            loginDetailsSet = true

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

        Company build() {
            validate()

            return new Company(this)
        }

        private void validate() {
            if (!idSet) throw new FieldNotSetException("ID must be set")
            if (!nameSet) throw new FieldNotSetException("Name must be set")
            if (!loginDetailsSet) throw new FieldNotSetException("Login details must be set")
            if (!descriptionSet) throw new FieldNotSetException("Description must be set")
            if (!cnpjSet) throw new FieldNotSetException("CNPJ must be set")
            if (!addressIdSet) throw new FieldNotSetException("Address ID must be set")
        }
    }
}
