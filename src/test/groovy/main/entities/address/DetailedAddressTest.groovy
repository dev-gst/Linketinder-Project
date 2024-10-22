package main.entities.address

import main.models.entities.address.DetailedAddress
import spock.lang.Specification

class DetailedAddressTest extends Specification {

    def "DetailedAddress should be created with all fields"() {
        given:
        def detailedAddress = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")

        expect:
        detailedAddress.neighborhood == "Bairro"
        detailedAddress.street == "Rua nenhuma"
        detailedAddress.number == "0"
        detailedAddress.zipCode == "12345"
    }

    def "DetailedAddress should not be created with null neighborhood"() {
        when:
        new DetailedAddress(null, "Rua nenhuma", "0", "12345")

        then:
        thrown(IllegalArgumentException)
    }

    def "DetailedAddress should not be created with null street"() {
        when:
        new DetailedAddress("Bairro", null, "0", "12345")

        then:
        thrown(IllegalArgumentException)
    }

    def "DetailedAddress should not be created with null number"() {
        when:
        new DetailedAddress("Bairro", "Rua nenhuma", null, "12345")

        then:
        thrown(IllegalArgumentException)
    }

    def "DetailedAddress should not be created with null zip code"() {
        when:
        new DetailedAddress("Bairro", "Rua nenhuma", "0", null)

        then:
        thrown(IllegalArgumentException)
    }

    def " DetailedAddress should be equal to another DetailedAddress with the same fields"() {
        given:
        def detailedAddress1 = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def detailedAddress2 = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")

        expect:
        detailedAddress1 == detailedAddress2
    }

    def "DetailedAddress should not be equal to another DetailedAddress with different field"() {
        given:
        def detailedAddress1 = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def detailedAddress2 = new DetailedAddress("Bairro2", "Rua nenhuma", "0", "12345")

        expect:
        detailedAddress1 != detailedAddress2
    }

    def "DetailedAddress should have the same hash code as another DetailedAddress with the same fields"() {
        given:
        def detailedAddress1 = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def detailedAddress2 = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")

        expect:
        detailedAddress1.hashCode() == detailedAddress2.hashCode()
    }

    def "DetailedAddress should not have the same hash code as another DetailedAddress with different fields"() {
        given:
        def detailedAddress1 = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def detailedAddress2 = new DetailedAddress("Bairro2", "Rua nenhuma", "0", "12345")

        expect:
        detailedAddress1.hashCode() != detailedAddress2.hashCode()
    }
}
