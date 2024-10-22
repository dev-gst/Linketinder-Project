package main.entities.address

import main.models.entities.address.DetailedAddress
import spock.lang.Specification

class DetailedAddressTest extends Specification {

    def "Detailed address should be created with all fields"() {
        given:
        def detailedAddress = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")

        expect:
        detailedAddress.neighborhood == "Bairro"
        detailedAddress.street == "Rua nenhuma"
        detailedAddress.number == "0"
        detailedAddress.zipCode == "12345"
    }

    def "Detailed address should not be created with null neighborhood"() {
        when:
        new DetailedAddress(null, "Rua nenhuma", "0", "12345")

        then:
        thrown(IllegalArgumentException)
    }

    def "Detailed address should not be created with null street"() {
        when:
        new DetailedAddress("Bairro", null, "0", "12345")

        then:
        thrown(IllegalArgumentException)
    }

    def "Detailed address should not be created with null number"() {
        when:
        new DetailedAddress("Bairro", "Rua nenhuma", null, "12345")

        then:
        thrown(IllegalArgumentException)
    }

    def "Detailed address should not be created with null zip code"() {
        when:
        new DetailedAddress("Bairro", "Rua nenhuma", "0", null)

        then:
        thrown(IllegalArgumentException)
    }

    def "Detailed address should not be created with blank neighborhood"() {
        when:
        new DetailedAddress("", "Rua nenhuma", "0", "12345")

        then:
        thrown(IllegalArgumentException)
    }

    def "Detailed address should not be created with blank street"() {
        when:
        new DetailedAddress("Bairro", "", "0", "12345")

        then:
        thrown(IllegalArgumentException)
    }

    def "Detailed address should not be created with blank number"() {
        when:
        new DetailedAddress("Bairro", "Rua nenhuma", "", "12345")

        then:
        thrown(IllegalArgumentException)
    }

    def "Detailed address should not be created with blank zip code"() {
        when:
        new DetailedAddress("Bairro", "Rua nenhuma", "0", "")

        then:
        thrown(IllegalArgumentException)
    }

    def " Detailed address should be equal to another Detailed address with the same fields"() {
        given:
        def detailedAddress1 = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def detailedAddress2 = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")

        expect:
        detailedAddress1 == detailedAddress2
    }

    def "Detailed address should not be equal to another Detailed address with different field"() {
        given:
        def detailedAddress1 = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def detailedAddress2 = new DetailedAddress("Bairro2", "Rua nenhuma", "0", "12345")

        expect:
        detailedAddress1 != detailedAddress2
    }

    def "Detailed address should have the same hash code as another Detailed address with the same fields"() {
        given:
        def detailedAddress1 = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def detailedAddress2 = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")

        expect:
        detailedAddress1.hashCode() == detailedAddress2.hashCode()
    }

    def "Detailed address should not have the same hash code as another Detailed address with different fields"() {
        given:
        def detailedAddress1 = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def detailedAddress2 = new DetailedAddress("Bairro2", "Rua nenhuma", "0", "12345")

        expect:
        detailedAddress1.hashCode() != detailedAddress2.hashCode()
    }
}
