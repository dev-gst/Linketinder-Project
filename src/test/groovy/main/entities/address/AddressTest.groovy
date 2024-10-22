package main.entities.address

import main.models.entities.address.Address
import main.models.entities.address.DetailedAddress
import spock.lang.Specification

class AddressTest extends Specification {

    def "Address should be created with all fields"() {
        given:
        def detailedAddress = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def address = new Address(1, "Brazil", "São Paulo", "São Paulo", detailedAddress)

        expect:
        address.id == 1
        address.country == "Brazil"
        address.region == "São Paulo"
        address.city == "São Paulo"
        address.detailedAddress == detailedAddress
    }

    def "Address should not be created with null country"() {
        when:
        new Address(1, null, "São Paulo", "São Paulo", new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345"))

        then:
        thrown(IllegalArgumentException)
    }

    def "Address should not be created with null region"() {
        when:
        new Address(1, "Brazil", null, "São Paulo", new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345"))

        then:
        thrown(IllegalArgumentException)
    }

    def "Address should not be created with null city"() {
        when:
        new Address(1, "Brazil", "São Paulo", null, new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345"))

        then:
        thrown(IllegalArgumentException)
    }

    def "Address should not be created with null detailed address"() {
        when:
        new Address(1, "Brazil", "São Paulo", "São Paulo", null)

        then:
        thrown(IllegalArgumentException)
    }

    def "Address should not be created with blank country"() {
        when:
        new Address(1, "", "São Paulo", "São Paulo", new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345"))

        then:
        thrown(IllegalArgumentException)
    }

    def "Address should not be created with blank region"() {
        when:
        new Address(1, "Brazil", "", "São Paulo", new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345"))

        then:
        thrown(IllegalArgumentException)
    }

    def "Address should not be created with blank city"() {
        when:
        new Address(1, "Brazil", "São Paulo", "", new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345"))

        then:
        thrown(IllegalArgumentException)
    }

    def "Address should be equal to another Address with the same fields"() {
        given:
        def detailedAddress = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def address1 = new Address(1, "Brazil", "São Paulo", "São Paulo", detailedAddress)
        def address2 = new Address(1, "Brazil", "São Paulo", "São Paulo", detailedAddress)

        expect:
        address1 == address2
    }

    def "Address should not be equal to another Address with different field"() {
        given:
        def detailedAddress = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def address1 = new Address(1, "Brazil", "São Paulo", "São Paulo", detailedAddress)
        def address2 = new Address(2, "Brazil", "São Paulo", "São Paulo", detailedAddress) // Different id

        expect:
        address1 != address2
    }

    def "Address should not be equal to another Address with different detailed address"() {
        given:
        def detailedAddress1 = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def detailedAddress2 = new DetailedAddress("Bairro2", "Rua nenhuma", "0", "12345") // Different neighborhood
        def address1 = new Address(1, "Brazil", "São Paulo", "São Paulo", detailedAddress1)
        def address2 = new Address(1, "Brazil", "São Paulo", "São Paulo", detailedAddress2)

        expect:
        address1 != address2
    }

    def "Address should have the same hash code as another Address with the same fields"() {
        given:
        def detailedAddress = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def address1 = new Address(1, "Brazil", "São Paulo", "São Paulo", detailedAddress)
        def address2 = new Address(1, "Brazil", "São Paulo", "São Paulo", detailedAddress)

        expect:
        address1.hashCode() == address2.hashCode()
    }

    def "Address should not have the same hash code as another Address with different fields"() {
        given:
        def detailedAddress = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def address1 = new Address(1, "Brazil", "São Paulo", "São Paulo", detailedAddress)
        def address2 = new Address(2, "Brazil", "São Paulo", "São Paulo", detailedAddress) // Different id

        expect:
        address1.hashCode() != address2.hashCode()
    }
}
