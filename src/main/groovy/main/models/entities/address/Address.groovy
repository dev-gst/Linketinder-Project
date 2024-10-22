package main.models.entities.address

import main.util.exception.ParamValidation

class Address {

    int id
    String country
    String region
    String city
    DetailedAddress detailedAddress

    Address(int id, String country, String region, String city, DetailedAddress detailedAddress) {
        ParamValidation.requireNonNull(country, "Country cannot be null")
        ParamValidation.requireNonNull(region, "Region cannot be null")
        ParamValidation.requireNonNull(city, "City cannot be null")
        ParamValidation.requireNonNull(detailedAddress, "Detailed address cannot be null")

        this.id = id
        this.country = country
        this.region = region
        this.city = city
        this.detailedAddress = detailedAddress
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        Address address = (Address) o

        if (id != address.id) return false
        if (city != address.city) return false
        if (country != address.country) return false
        if (detailedAddress != address.detailedAddress) return false
        if (region != address.region) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = id
        result = 31 * result + (country != null ? country.hashCode() : 0)
        result = 31 * result + (region != null ? region.hashCode() : 0)
        result = 31 * result + (city != null ? city.hashCode() : 0)
        result = 31 * result + (detailedAddress != null ? detailedAddress.hashCode() : 0)
        return result
    }
}
