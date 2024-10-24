package main.models.dtos.anonresponse

import main.util.exception.ParamValidation

class AnonAddressDTO {

    private final String country
    private final String state
    private final String city

    AnonAddressDTO(String country, String state, String city) {
        ParamValidation.requireNonBlank(country, "Country cannot be null or blank")
        ParamValidation.requireNonBlank(state, "State cannot be null or blank")
        ParamValidation.requireNonBlank(city, "City cannot be null or blank")

        this.country = country
        this.state = state
        this.city = city
    }

    String getCountry() {
        return country
    }

    String getState() {
        return state
    }

    String getCity() {
        return city
    }
}
