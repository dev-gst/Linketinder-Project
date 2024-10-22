package main.models.entities.address

class Location {

    int id
    String country
    String region
    String city
    DetailedAddress detailedAddress

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
