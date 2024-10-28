package main.models.entities.address

import main.util.exception.ParamValidation
import main.util.exception.custom.FieldNotSetException

class Address {

    int id
    String country
    String region
    String city
    String neighborhood
    String street
    String number
    String zipCode

    private Address(Builder builder) {
        this.id = builder.id
        this.country = builder.country
        this.region = builder.region
        this.city = builder.city
        this.neighborhood = builder.neighborhood
        this.street = builder.street
        this.number = builder.number
        this.zipCode = builder.zipCode
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        Address address = (Address) o

        if (id != address.id) return false
        if (city != address.city) return false
        if (country != address.country) return false
        if (neighborhood != address.neighborhood) return false
        if (number != address.number) return false
        if (region != address.region) return false
        if (street != address.street) return false
        if (zipCode != address.zipCode) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = id
        result = 31 * result + (country != null ? country.hashCode() : 0)
        result = 31 * result + (region != null ? region.hashCode() : 0)
        result = 31 * result + (city != null ? city.hashCode() : 0)
        result = 31 * result + (neighborhood != null ? neighborhood.hashCode() : 0)
        result = 31 * result + (street != null ? street.hashCode() : 0)
        result = 31 * result + (number != null ? number.hashCode() : 0)
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0)
        return result
    }

    static class Builder {

        int id
        String country
        String region
        String city
        String neighborhood
        String street
        String number
        String zipCode

        private boolean idSet = false
        private boolean countrySet = false
        private boolean regionSet = false
        private boolean citySet = false
        private boolean neighborhoodSet = false
        private boolean streetSet = false
        private boolean numberSet = false
        private boolean zipCodeSet = false

        Builder id(int id) {
            ParamValidation.requirePositive(id, "ID cannot be negative")

            this.id = id
            this.idSet = true

            return this
        }

        Builder country(String country) {
            ParamValidation.requireNonBlank(country, "Country cannot be null or blank")

            this.countrySet = true
            this.country = country

            return this
        }

        Builder region(String region) {
            ParamValidation.requireNonBlank(region, "Region cannot be null or blank")

            this.region = region
            this.regionSet = true

            return this
        }

        Builder city(String city) {
            ParamValidation.requireNonBlank(city, "City cannot be null or blank")

            this.city = city
            this.citySet = true

            return this
        }

        Builder neighborhood(String neighborhood) {
            ParamValidation.requireNonBlank(neighborhood, "Neighborhood cannot be null or blank")

            this.neighborhood = neighborhood
            this.neighborhoodSet = true

            return this
        }

        Builder street(String street) {
            ParamValidation.requireNonBlank(street, "Street cannot be null or blank")

            this.street = street
            this.streetSet = true

            return this
        }

        Builder number(String number) {
            ParamValidation.requireNonBlank(number, "Number cannot be null or blank")

            this.number = number
            this.numberSet = true

            return this
        }

        Builder zipCode(String zipCode) {
            ParamValidation.requireNonBlank(zipCode, "Zip code cannot be null or blank")

            this.zipCode = zipCode
            this.zipCodeSet = true

            return this
        }

        Address build() {
            validateFields()
            return new Address(this)
        }

        private validateFields() {
            if (!idSet) throw new FieldNotSetException("ID must be set")
            if (!countrySet) throw new FieldNotSetException("Country must be set")
            if (!regionSet) throw new FieldNotSetException("Region must be set")
            if (!citySet) throw new FieldNotSetException("City must be set")
            if (!neighborhoodSet) throw new FieldNotSetException("Neighborhood must be set")
            if (!streetSet) throw new FieldNotSetException("Street must be set")
            if (!numberSet) throw new FieldNotSetException("Number must be set")
            if (!zipCodeSet) throw new FieldNotSetException("Zip code must be set")
        }
    }
}
