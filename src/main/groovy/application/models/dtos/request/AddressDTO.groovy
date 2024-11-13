package application.models.dtos.request

import application.utils.exceptions.FieldNotSetException
import application.utils.validation.ParamValidation

class AddressDTO {

    final String country
    final String region
    final String city
    final String neighborhood
    final String street
    final String number
    final String zipCode

    private AddressDTO(Builder builder) {
        this.country = builder.country
        this.region = builder.region
        this.city = builder.city
        this.neighborhood = builder.neighborhood
        this.street = builder.street
        this.number = builder.number
        this.zipCode = builder.zipCode
    }

    String getCountry() {
        return country
    }

    String getRegion() {
        return region
    }

    String getCity() {
        return city
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

    static AddressDTO of(Map<String, String> addressInfo) {
        return new Builder()
                .country(addressInfo.get("country"))
                .region(addressInfo.get("region"))
                .city(addressInfo.get("city"))
                .neighborhood(addressInfo.get("neighborhood"))
                .street(addressInfo.get("street"))
                .number(addressInfo.get("number"))
                .zipCode(addressInfo.get("zipCode"))
                .build()
    }

    static class Builder {

        String country
        String region
        String city
        String neighborhood
        String street
        String number
        String zipCode

        private boolean countrySet = false
        private boolean regionSet = false
        private boolean citySet = false
        private boolean neighborhoodSet = false
        private boolean streetSet = false
        private boolean numberSet = false
        private boolean zipCodeSet = false

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

        AddressDTO build() {
            validateFields()
            return new AddressDTO(this)
        }

        private validateFields() {
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
