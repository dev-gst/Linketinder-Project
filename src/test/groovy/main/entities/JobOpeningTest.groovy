package main.entities

import main.models.entities.JobOpening
import spock.lang.Specification

class JobOpeningTest extends Specification {

    def "job opening constructor returns new instance"() {
        given:
        JobOpening jobOpening = new JobOpening.Builder()
                .setId(1)
                .setName("name")
                .setDescription("description")
                .setIsOpen(true)
                .setIsRemote(false)
                .setCompanyId(1)
                .setAddressId(Optional.of(1))
                .build()

        then:
        jobOpening != null
    }

    def "job opening constructor throws exception when id is negative"() {
        given:
        new JobOpening.Builder()
                .setId(-1)
                .setName("name")
                .setDescription("description")
                .setIsOpen(true)
                .setIsRemote(false)
                .setCompanyId(1)
                .setAddressId(Optional.of(1))

        expect:
        thrown(IllegalArgumentException)
    }

    def "job opening constructor throws exception when name is null"() {
        when:
        new JobOpening.Builder()
                .setId(1)
                .setName(null)
                .setDescription("description")
                .setIsOpen(true)
                .setIsRemote(false)
                .setCompanyId(1)
                .setAddressId(Optional.of(1))

        then:
        thrown(IllegalArgumentException)
    }

    def "job opening constructor throws exception when companyId is negative"() {
        given:
        new JobOpening.Builder()
                .setId(1)
                .setName("name")
                .setDescription("description")
                .setIsOpen(true)
                .setIsRemote(false)
                .setCompanyId(-1)
                .setAddressId(Optional.of(1))

        expect:
        thrown(IllegalArgumentException)
    }

    def "job opening equals returns true when objects are equal"() {
        given:
        JobOpening jobOpening1 = new JobOpening.Builder()
                .setId(1)
                .setName("name")
                .setDescription("description")
                .setIsOpen(true)
                .setIsRemote(false)
                .setCompanyId(1)
                .setAddressId(Optional.of(1))
                .build()

        JobOpening jobOpening2 = new JobOpening.Builder()
                .setId(1)
                .setName("name")
                .setDescription("description")
                .setIsOpen(true)
                .setIsRemote(false)
                .setCompanyId(1)
                .setAddressId(Optional.of(1))
                .build()

        expect:
        jobOpening1 == jobOpening2
    }

    def "job opening equals returns false when objects are not equal"() {
        given:
        JobOpening jobOpening1 = new JobOpening.Builder()
                .setId(1)
                .setName("name")
                .setDescription("description")
                .setIsOpen(true)
                .setIsRemote(false)
                .setCompanyId(1)
                .setAddressId(Optional.of(1))
                .build()

        JobOpening jobOpening2 = new JobOpening.Builder()
                .setId(2)
                .setName("name")
                .setDescription("description")
                .setIsOpen(true)
                .setIsRemote(false)
                .setCompanyId(1)
                .setAddressId(Optional.of(1))
                .build()

        expect:
        jobOpening1 == jobOpening2
    }

    def "job opening hashcode returns same value when objects are equal"() {
        given:
        JobOpening jobOpening1 = new JobOpening.Builder()
                .setId(1)
                .setName("name")
                .setDescription("description")
                .setIsOpen(true)
                .setIsRemote(false)
                .setCompanyId(1)
                .setAddressId(Optional.of(1))
                .build()

        JobOpening jobOpening2 = new JobOpening.Builder()
                .setId(1)
                .setName("name")
                .setDescription("description")
                .setIsOpen(true)
                .setIsRemote(false)
                .setCompanyId(1)
                .setAddressId(Optional.of(1))
                .build()

        expect:
        jobOpening1.hashCode() == jobOpening2.hashCode()
    }

    def "job opening hashcode returns different value when objects are not equal"() {
        given:
        JobOpening jobOpening1 = new JobOpening.Builder()
                .setId(1)
                .setName("name")
                .setDescription("description")
                .setIsOpen(true)
                .setIsRemote(false)
                .setCompanyId(1)
                .setAddressId(Optional.of(1))
                .build()

        JobOpening jobOpening2 = new JobOpening.Builder()
                .setId(1)
                .setName("name")
                .setDescription("description")
                .setIsOpen(true)
                .setIsRemote(false)
                .setCompanyId(1)
                .setAddressId(Optional.of(1))
                .build()

        expect:
        jobOpening1.hashCode() != jobOpening2.hashCode()
    }
}
