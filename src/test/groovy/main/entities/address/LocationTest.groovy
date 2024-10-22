package main.entities.address

import main.models.entities.address.DetailedAddress
import main.models.entities.address.Location
import spock.lang.Specification

class LocationTest extends Specification {

    def "Location should be created with all fields"() {
        given:
        def detailedAddress = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def location = new Location(1, "Brazil", "São Paulo", "São Paulo", detailedAddress)

        expect:
        location.id == 1
        location.country == "Brazil"
        location.region == "São Paulo"
        location.city == "São Paulo"
        location.detailedAddress == detailedAddress
    }

    def "Location should not be created with null country"() {
        when:
        new Location(1, null, "São Paulo", "São Paulo", new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345"))

        then:
        thrown(IllegalArgumentException)
    }

    def "Location should not be created with null region"() {
        when:
        new Location(1, "Brazil", null, "São Paulo", new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345"))

        then:
        thrown(IllegalArgumentException)
    }

    def "Location should not be created with null city"() {
        when:
        new Location(1, "Brazil", "São Paulo", null, new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345"))

        then:
        thrown(IllegalArgumentException)
    }

    def "Location should not be created with null detailed address"() {
        when:
        new Location(1, "Brazil", "São Paulo", "São Paulo", null)

        then:
        thrown(IllegalArgumentException)
    }

    def "Location should be equal to another Location with the same fields"() {
        given:
        def detailedAddress = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def location1 = new Location(1, "Brazil", "São Paulo", "São Paulo", detailedAddress)
        def location2 = new Location(1, "Brazil", "São Paulo", "São Paulo", detailedAddress)

        expect:
        location1 == location2
    }

    def "Location should not be equal to another Location with different field"() {
        given:
        def detailedAddress = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def location1 = new Location(1, "Brazil", "São Paulo", "São Paulo", detailedAddress)
        def location2 = new Location(2, "Brazil", "São Paulo", "São Paulo", detailedAddress) // Different id

        expect:
        location1 != location2
    }

    def "Location should not be equal to another Location with different detailed address"() {
        given:
        def detailedAddress1 = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def detailedAddress2 = new DetailedAddress("Bairro2", "Rua nenhuma", "0", "12345") // Different neighborhood
        def location1 = new Location(1, "Brazil", "São Paulo", "São Paulo", detailedAddress1)
        def location2 = new Location(1, "Brazil", "São Paulo", "São Paulo", detailedAddress2)

        expect:
        location1 != location2
    }

    def "Location should have the same hash code as another Location with the same fields"() {
        given:
        def detailedAddress = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def location1 = new Location(1, "Brazil", "São Paulo", "São Paulo", detailedAddress)
        def location2 = new Location(1, "Brazil", "São Paulo", "São Paulo", detailedAddress)

        expect:
        location1.hashCode() == location2.hashCode()
    }

    def "Location should not have the same hash code as another Location with different fields"() {
        given:
        def detailedAddress = new DetailedAddress("Bairro", "Rua nenhuma", "0", "12345")
        def location1 = new Location(1, "Brazil", "São Paulo", "São Paulo", detailedAddress)
        def location2 = new Location(2, "Brazil", "São Paulo", "São Paulo", detailedAddress) // Different id

        expect:
        location1.hashCode() != location2.hashCode()
    }
}
