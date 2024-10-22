package main.models.entities.address

import main.util.exception.ParamValidation

class DetailedAddress {

    String neighborhood
    String street
    String number
    String zipCode

    DetailedAddress(String neighborhood, String street, String number, String zipCode) {
        ParamValidation.requireNonNull(neighborhood, "Neighborhood cannot be null")
        ParamValidation.requireNonNull(street, "Street cannot be null")
        ParamValidation.requireNonNull(number, "Number cannot be null")
        ParamValidation.requireNonNull(zipCode, "Zip code cannot be null")

        this.neighborhood = neighborhood
        this.street = street
        this.number = number
        this.zipCode = zipCode
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        DetailedAddress that = (DetailedAddress) o

        if (neighborhood != that.neighborhood) return false
        if (number != that.number) return false
        if (street != that.street) return false
        if (zipCode != that.zipCode) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = (neighborhood != null ? neighborhood.hashCode() : 0)
        result = 31 * result + (street != null ? street.hashCode() : 0)
        result = 31 * result + (number != null ? number.hashCode() : 0)
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0)
        return result
    }
}
