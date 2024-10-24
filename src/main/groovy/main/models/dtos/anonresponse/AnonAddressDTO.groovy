package main.models.dtos.anonresponse

import main.util.exception.ParamValidation

class AnonAddressDTO {

    private final String country
    private final String region
    private final String city

    AnonAddressDTO(String country, String region, String city) {
        ParamValidation.requireNonBlank(country, "Country cannot be null or blank")
        ParamValidation.requireNonBlank(region, "State cannot be null or blank")
        ParamValidation.requireNonBlank(city, "City cannot be null or blank")

        this.country = country
        this.region = region
        this.city = city
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
}
