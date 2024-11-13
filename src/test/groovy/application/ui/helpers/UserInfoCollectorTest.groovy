package application.ui.helpers

import application.mocks.CandidateMockFactory
import application.models.entities.Candidate
import spock.lang.Specification

class UserInfoCollectorTest extends Specification {


    def "gatherAddressInfo returns correct address info"() {
        given:
        Scanner scanner = new Scanner("Brazil\nSP\nSao Paulo\nCentro\nPaulista\n123\n01000-000\n")

        when:
        Map<String, String> result = UserInfoCollector.gatherAddressInfo(scanner)

        then:
        result == [
                country     : "Brazil",
                region      : "SP",
                city        : "Sao Paulo",
                neighborhood: "Centro",
                street      : "Paulista",
                number      : "123",
                zipCode     : "01000-000"
        ]
    }

    def "updateAddressInfo returns null when not updating"() {
        given:
        Scanner scanner = new Scanner("n\n")

        when:
        Map<String, String> result = UserInfoCollector.updateAddressInfo(scanner)

        then:
        result == null
    }

    def "updateAddressInfo returns updated address info"() {
        given:
        Scanner scanner = new Scanner("s\nBrazil\nSP\nSao Paulo\nCentro\nPaulista\n123\n01000-000\n")

        when:
        Map<String, String> result = UserInfoCollector.updateAddressInfo(scanner)

        then:
        result == [
                country     : "Brazil",
                region      : "SP",
                city        : "Sao Paulo",
                neighborhood: "Centro",
                street      : "Paulista",
                number      : "123",
                zipCode     : "01000-000"
        ]
    }

    def "gatherSkills returns correct skills"() {
        given:
        Scanner scanner = new Scanner("Java\nGroovy\nsair\n")

        when:
        Set<String> result = UserInfoCollector.gatherSkills(scanner)

        then:
        result == ["Java", "Groovy"] as Set
    }

    def "updateSkills returns null when not updating"() {
        given:
        Scanner scanner = new Scanner("n\n")

        when:
        Set<String> result = UserInfoCollector.updateSkills(scanner)

        then:
        result == null
    }

    def "updateSkills returns updated skills"() {
        given:
        Scanner scanner = new Scanner("s\nJava\nGroovy\nsair\n")

        when:
        Set<String> result = UserInfoCollector.updateSkills(scanner)

        then:
        result == ["Java", "Groovy"] as Set
    }

    def "gatherCandidateInfo returns correct candidate info"() {
        given:
        Scanner scanner = new Scanner("John\nDoe\njohn.doe@example.com\npassword\nBIO\n2000-01-01\n12345678900\nBachelor\n")

        when:
        Map<String, String> result = UserInfoCollector.gatherCandidateInfo(scanner)

        then:
        result == [
                firstName  : "John",
                lastName   : "Doe",
                email      : "john.doe@example.com",
                password   : "password",
                cpf        : "12345678900",
                birthDate: "2000-01-01",
                description: "BIO",
                education  : "Bachelor"
        ]
    }

    def "updateCandidateInfo returns updated candidate info"() {
        given:
        Candidate candidate = new CandidateMockFactory().createCandidateMock(1)
        Scanner scanner = new Scanner("s\nJane\ns\nDoe\ns\njane.doe@example.com\ns\nnewpassword\ns\nNew BIO\ns\n1990-01-01\ns\n98765432100\ns\nMaster\n")

        when:
        Map<String, String> result = UserInfoCollector.updateCandidateInfo(candidate, scanner)

        then:
        result == [
                firstName  : "Jane",
                lastName   : "Doe",
                email      : "jane.doe@example.com",
                password   : "newpassword",
                cpf        : "98765432100",
                birthDate: "1990-01-01",
                description: "New BIO",
                education  : "Master"
        ]
    }
}
