package Linketinder.models.entities

class Address {
    String country
    String state
    String zipCode

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        Address address = (Address) o

        if (country != address.country) return false
        if (state != address.state) return false
        if (zipCode != address.zipCode) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = (country != null ? country.hashCode() : 0)
        result = 31 * result + (state != null ? state.hashCode() : 0)
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0)
        return result
    }

    @Override
    String toString() {
        return "Country: $country, " +
                "\nState: $state, " +
                "\nZIP CODE: $zipCode"
    }
}
