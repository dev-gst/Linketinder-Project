package main.entities.jobOpening

import main.models.entities.jobOpening.JobOpening
import main.models.entities.jobOpening.JobOpeningDetails
import spock.lang.Specification

class JobOpeningTest extends Specification {

    def "job opening constructor returns new instance"() {
        given:
        JobOpeningDetails jobOpeningDetails = Mock(JobOpeningDetails)

        when:
        JobOpening jobOpening = new JobOpening(1, jobOpeningDetails, 1)

        then:
        jobOpening != null
    }

    def "job opening constructor throws exception when id is negative"() {
        given:
        JobOpeningDetails jobOpeningDetails = Mock(JobOpeningDetails)

        when:
        new JobOpening(-1, jobOpeningDetails, 1)

        then:
        thrown(IllegalArgumentException)
    }

    def "job opening constructor throws exception when jobOpeningDetails is null"() {
        when:
        new JobOpening(1, null, 1)

        then:
        thrown(IllegalArgumentException)
    }

    def "job opening constructor throws exception when companyId is negative"() {
        given:
        JobOpeningDetails jobOpeningDetails = Mock(JobOpeningDetails)

        when:
        new JobOpening(1, jobOpeningDetails, -1)

        then:
        thrown(IllegalArgumentException)
    }

    def "job opening equals returns true when objects are equal"() {
        given:
        JobOpeningDetails jobOpeningDetails = Mock(JobOpeningDetails)
        JobOpening jobOpening1 = new JobOpening(1, jobOpeningDetails, 1)
        JobOpening jobOpening2 = new JobOpening(1, jobOpeningDetails, 1)

        expect:
        jobOpening1 == jobOpening2
    }

    def "job opening equals returns false when objects are not equal"() {
        given:
        JobOpeningDetails jobOpeningDetails = Mock(JobOpeningDetails)
        JobOpening jobOpening1 = new JobOpening(1, jobOpeningDetails, 1)
        JobOpening jobOpening2 = new JobOpening(2, jobOpeningDetails, 1)

        expect:
        jobOpening1 != jobOpening2
    }

    def "job opening hashcode returns same value when objects are equal"() {
        given:
        JobOpeningDetails jobOpeningDetails = Mock(JobOpeningDetails)
        JobOpening jobOpening1 = new JobOpening(1, jobOpeningDetails, 1)
        JobOpening jobOpening2 = new JobOpening(1, jobOpeningDetails, 1)

        expect:
        jobOpening1.hashCode() == jobOpening2.hashCode()
    }

    def "job opening hashcode returns different value when objects are not equal"() {
        given:
        JobOpeningDetails jobOpeningDetails = Mock(JobOpeningDetails)
        JobOpening jobOpening1 = new JobOpening(1, jobOpeningDetails, 1)
        JobOpening jobOpening2 = new JobOpening(2, jobOpeningDetails, 1)

        expect:
        jobOpening1.hashCode() != jobOpening2.hashCode()
    }
}
