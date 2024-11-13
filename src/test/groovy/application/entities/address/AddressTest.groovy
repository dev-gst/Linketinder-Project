package application.entities.address

import application.models.entities.Address
import application.utils.exceptions.FieldNotSetException
import spock.lang.Specification

class AddressTest extends Specification {

    def "Address should be created with all fields"() {
        given:
        Address address = new Address.Builder()
                .id(1)
                .country("Brazil")
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number("0")
                .zipCode("12345")
                .build()

        expect:
        address.id == 1
        address.country == "Brazil"
        address.region == "São Paulo"
        address.city == "São Paulo"
        address.neighborhood == "Bairro"
        address.street == "Rua nenhuma"
        address.number == "0"
        address.zipCode == "12345"
    }

    def "Address should not be created without field"() {
        when:
        new Address.Builder()
                .id(1)
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number("0")
                .zipCode("12345")
                .build()

        then:
        thrown(FieldNotSetException)
    }

    def "Address should not be created with null country"() {
        when:
        new Address.Builder()
                .id(1)
                .country(null)
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number("0")
                .zipCode("12345")
                .build()

        then:
        thrown(IllegalArgumentException)
    }

    def "Address should not be created with null region"() {
        when:
        new Address.Builder()
                .id(1)
                .country("Brazil")
                .region(null)
                .city("São Paulo")
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number("0")
                .zipCode("12345")
                .build()

        then:
        thrown(IllegalArgumentException)
    }

    def "Address should not be created with null city"() {
        when:
        new Address.Builder()
                .id(1)
                .country("Brazil")
                .region("São Paulo")
                .city(null)
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number("0")
                .zipCode("12345")
                .build()

        then:
        thrown(IllegalArgumentException)
    }

    def "Address should not be created with null neighborhood"() {
        when:
        new Address.Builder()
                .id(1)
                .country("Brazil")
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood(null)
                .street("Rua nenhuma")
                .number("0")
                .zipCode("12345")
                .build()

        then:
        thrown(IllegalArgumentException)
    }

    def "Address should not be created with null street"() {
        when:
        new Address.Builder()
                .id(1)
                .country("Brazil")
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood("Bairro")
                .street(null)
                .number("0")
                .zipCode("12345")
                .build()

        then:
        thrown(IllegalArgumentException)
    }

    def "Address should not be created with null number"() {
        when:
        new Address.Builder()
                .id(1)
                .country("Brazil")
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number(null)
                .zipCode("12345")
                .build()

        then:
        thrown(IllegalArgumentException)
    }

    def "Address should not be created with null zip code"() {
        when:
        new Address.Builder()
                .id(1)
                .country("Brazil")
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number("0")
                .zipCode(null)
                .build()

        then:
        thrown(IllegalArgumentException)
    }

    def "Address should be equal to another Address with the same fields"() {
        given:
        Address address1 = new Address.Builder()
                .id(1)
                .country("Brazil")
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number("0")
                .zipCode("12345")
                .build()

        Address address2 = new Address.Builder()
                .id(1)
                .country("Brazil")
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number("0")
                .zipCode("12345")
                .build()

        expect:
        address1 == address2
    }

    def "Address should not be equal to another Address with different field"() {
        given:
        Address address1 = new Address.Builder()
                .id(1)
                .country("Brazil")
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number("0")
                .zipCode("12345")
                .build()

        Address address2 = new Address.Builder()
                .id(2)
                .country("Brazil")
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number("0")
                .zipCode("12345")
                .build()

        expect:
        address1 != address2
    }


    def "Address should have the same hash code as another Address with the same fields"() {
        given:
        Address address1 = new Address.Builder()
                .id(1)
                .country("Brazil")
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number("0")
                .zipCode("12345")
                .build()

        Address address2 = new Address.Builder()
                .id(1)
                .country("Brazil")
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number("0")
                .zipCode("12345")
                .build()

        expect:
        address1.hashCode() == address2.hashCode()
    }

    def "Address should not have the same hash code as another Address with different fields"() {
        given:
        Address address1 = new Address.Builder()
                .id(1)
                .country("Brazil")
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number("0")
                .zipCode("12345")
                .build()

        Address address2 = new Address.Builder()
                .id(2)
                .country("Brazil")
                .region("São Paulo")
                .city("São Paulo")
                .neighborhood("Bairro")
                .street("Rua nenhuma")
                .number("0")
                .zipCode("12345")
                .build()

        expect:
        address1.hashCode() != address2.hashCode()
    }
}
