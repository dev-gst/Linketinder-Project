package main.models.entities.address

class Location {

    int id
    String country
    String region
    String city
    DetailedAddress detailedAddress

    Location(int id, String country, String region, String city, DetailedAddress detailedAddress) {
        Objects.requireNonNull(country, "Country cannot be null")
        Objects.requireNonNull(region, "Region cannot be null")
        Objects.requireNonNull(city, "City cannot be null")
        Objects.requireNonNull(detailedAddress, "Detailed address cannot be null")

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

        Location address = (Location) o

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
