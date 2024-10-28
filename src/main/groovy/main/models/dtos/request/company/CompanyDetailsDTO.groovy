package main.models.dtos.request.company

import main.models.dtos.request.AddressDTO
import main.util.exception.ParamValidation

class CompanyDetailsDTO {

    private final String name
    private final String description
    private final String cnpj

    // TODO: Address id only
    private final AddressDTO addressDTO

    CompanyDetailsDTO(String name, String description, String cnpj, AddressDTO addressDTO) {
        ParamValidation.requireNonBlank(name, "Name cannot be null or blank")
        ParamValidation.requireNonBlank(description, "Description cannot be null or blank")
        ParamValidation.requireNonBlank(cnpj, "CNPJ cannot be null or blank")

        this.name = name
        this.description = description
        this.cnpj = cnpj
        this.addressDTO = addressDTO
    }

    String getName() {
        return name
    }

    String getDescription() {
        return description
    }

    String getCnpj() {
        return cnpj
    }

    AddressDTO getAddressDTO() {
        return addressDTO
    }
}
