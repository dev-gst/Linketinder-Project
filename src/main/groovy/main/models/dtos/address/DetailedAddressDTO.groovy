package main.models.dtos.address

import main.util.exception.ParamValidation

class DetailedAddressDTO {

    private final String neighborhood
    private final String street
    private final String number
    private final String zipCode

    DetailedAddressDTO(String neighborhood, String street, String number, String zipCode) {
        ParamValidation.requireNonNull(neighborhood, "Neighborhood cannot be null")
        ParamValidation.requireNonNull(street, "Street cannot be null")
        ParamValidation.requireNonNull(number, "Number cannot be null")
        ParamValidation.requireNonNull(zipCode, "ZipCode cannot be null")

        this.neighborhood = neighborhood
        this.street = street
        this.number = number
        this.zipCode = zipCode
    }

    String getNeighborhood() {
        return neighborhood
    }

    String getStreet() {
        return street
    }

    String getNumber() {
        return number
    }

    String getZipCode() {
        return zipCode
    }
}
