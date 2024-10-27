package main.entities.jobOpening

import main.models.entities.jobOpening.JobOpeningAddress
import main.models.entities.jobOpening.JobOpeningDetails
import spock.lang.Specification

class JobOpeningDetailsTest extends Specification {

    def "job opening details constructor returns new instance"() {
        given:
        JobOpeningAddress jobOpeningAddress = Mock(JobOpeningAddress)

        when:
        JobOpeningDetails jobOpeningDetails = new JobOpeningDetails("title", "description", true, jobOpeningAddress)

        then:
        jobOpeningDetails != null
    }

    def "job opening details constructor throws exception when name is null"() {
        given:
        JobOpeningAddress jobOpeningAddress = Mock(JobOpeningAddress)

        when:
        new JobOpeningDetails(null, "description", true, jobOpeningAddress)

        then:
        thrown(IllegalArgumentException)
    }

    def "job opening details constructor throws exception when description is null"() {
        given:
        JobOpeningAddress jobOpeningAddress = Mock(JobOpeningAddress)

        when:
        new JobOpeningDetails("title", null, true, jobOpeningAddress)

        then:
        thrown(IllegalArgumentException)
    }

    def "job opening details constructor throws exception when jobOpeningAddress is null"() {
        when:
        new JobOpeningDetails("title", "description", true, null)

        then:
        thrown(IllegalArgumentException)
    }

    def "job opening details equals returns true when objects are equal"() {
        given:
        JobOpeningAddress jobOpeningAddress = Mock(JobOpeningAddress)
        JobOpeningDetails jobOpeningDetails1 = new JobOpeningDetails("title", "description", true, jobOpeningAddress)
        JobOpeningDetails jobOpeningDetails2 = new JobOpeningDetails("title", "description", true, jobOpeningAddress)

        expect:
        jobOpeningDetails1 == jobOpeningDetails2
    }

    def "job opening details equals returns false when objects are not equal"() {
        given:
        JobOpeningAddress jobOpeningAddress = Mock(JobOpeningAddress)
        JobOpeningDetails jobOpeningDetails1 = new JobOpeningDetails("title", "description", true, jobOpeningAddress)
        JobOpeningDetails jobOpeningDetails2 = new JobOpeningDetails("title", "description", false, jobOpeningAddress)

        expect:
        jobOpeningDetails1 != jobOpeningDetails2
    }

    def "job opening details hashcode returns same value when objects are equal"() {
        given:
        JobOpeningAddress jobOpeningAddress = Mock(JobOpeningAddress)
        JobOpeningDetails jobOpeningDetails1 = new JobOpeningDetails("title", "description", true, jobOpeningAddress)
        JobOpeningDetails jobOpeningDetails2 = new JobOpeningDetails("title", "description", true, jobOpeningAddress)

        expect:
        jobOpeningDetails1.hashCode() == jobOpeningDetails2.hashCode()
    }

    def "job opening details hashcode returns different value when objects are not equal"() {
        given:
        JobOpeningAddress jobOpeningAddress = Mock(JobOpeningAddress)
        JobOpeningDetails jobOpeningDetails1 = new JobOpeningDetails("title", "description", true, jobOpeningAddress)
        JobOpeningDetails jobOpeningDetails2 = new JobOpeningDetails("title", "description", false, jobOpeningAddress)

        expect:
        jobOpeningDetails1.hashCode() != jobOpeningDetails2.hashCode()
    }
}
