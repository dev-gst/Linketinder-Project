package main.entities.jobOpening

import main.models.entities.jobOpening.JobOpeningAddress
import spock.lang.Specification

class JobOpeningAddressTest extends Specification {

    def "job opening address constructor returns new instance"() {
        when:
        JobOpeningAddress jobOpeningAddress = new JobOpeningAddress(0, true) // remote job

        then:
        jobOpeningAddress != null
    }

    def "job opening address constructor returns new instance when remote is false"() {
        when:
        JobOpeningAddress jobOpeningAddress = new JobOpeningAddress(3, false) // not remote job

        then:
        jobOpeningAddress != null
    }

    def "job opening address constructor throws exception when addressId is negative"() {
        when:
        new JobOpeningAddress(-1, false)

        then:
        thrown(IllegalArgumentException)
    }

    def "job opening address equals returns true when objects are equal"() {
        given:
        JobOpeningAddress jobOpeningAddress1 = new JobOpeningAddress(1, true)
        JobOpeningAddress jobOpeningAddress2 = new JobOpeningAddress(1, true)

        expect:
        jobOpeningAddress1 == jobOpeningAddress2
    }

    def "job opening address equals returns false when objects are not equal"() {
        given:
        JobOpeningAddress jobOpeningAddress1 = new JobOpeningAddress(1, true)
        JobOpeningAddress jobOpeningAddress2 = new JobOpeningAddress(2, true)

        expect:
        jobOpeningAddress1 != jobOpeningAddress2
    }

    def "job opening address hashcode returns same value when objects are equal"() {
        given:
        JobOpeningAddress jobOpeningAddress1 = new JobOpeningAddress(1, true)
        JobOpeningAddress jobOpeningAddress2 = new JobOpeningAddress(1, true)

        expect:
        jobOpeningAddress1.hashCode() == jobOpeningAddress2.hashCode()
    }

    def "job opening address hashcode returns different value when objects are not equal"() {
        given:
        JobOpeningAddress jobOpeningAddress1 = new JobOpeningAddress(1, true)
        JobOpeningAddress jobOpeningAddress2 = new JobOpeningAddress(2, true)

        expect:
        jobOpeningAddress1.hashCode() != jobOpeningAddress2.hashCode()
    }
}
