package main.models.dtos.address

import main.util.exception.ParamValidation

class AddressDTO {

    private final String country
    private final String region
    private final String city
    private final DetailedAddressDTO detailedAddressDTO

    AddressDTO(String country, String region, String city, DetailedAddressDTO detailedAddressDTO) {
        ParamValidation.requireNonBlank(country, "Country cannot be null")
        ParamValidation.requireNonBlank(region, "Region cannot be null")
        ParamValidation.requireNonBlank(city, "City cannot be null")
        ParamValidation.requireNonNull(detailedAddressDTO, "DetailedAddressDTO cannot be null")

        this.country = country
        this.region = region
        this.city = city
        this.detailedAddressDTO = detailedAddressDTO
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

    DetailedAddressDTO getDetailedAddressDTO() {
        return detailedAddressDTO
    }
}
