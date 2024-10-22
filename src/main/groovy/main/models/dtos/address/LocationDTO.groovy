package main.models.dtos.address

class LocationDTO {

    private final String country
    private final String region
    private final String city
    private final DetailedAddressDTO detailedAddressDTO

    LocationDTO(String country, String region, String city, DetailedAddressDTO detailedAddressDTO) {
        Objects.requireNonNull(country, "Country cannot be null")
        Objects.requireNonNull(region, "Region cannot be null")
        Objects.requireNonNull(city, "City cannot be null")
        Objects.requireNonNull(detailedAddressDTO, "DetailedAddressDTO cannot be null")

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
