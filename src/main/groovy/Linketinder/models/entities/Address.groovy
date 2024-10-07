package Linketinder.models.entities

class Address {
    int id
    String country
    String region
    String city
    String neighborhood
    String street
    String number
    String zipCode

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

    @Override
    String toString() {
        return "Endereço:\n" +
                "ID: ${id}\n" +
                "País: ${country}\n" +
                "Região: ${region}\n" +
                "Cidade: ${city}\n" +
                "Bairro: ${neighborhood}\n" +
                "Rua: ${street}\n" +
                "Número: ${number}\n" +
                "CEP: ${zipCode}"
    }
}
