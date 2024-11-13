package application.entities

import application.models.entities.Candidate
import application.models.entities.login.LoginDetails
import application.utils.exceptions.FieldNotSetException
import spock.lang.Specification

import java.time.LocalDate
import java.time.ZoneId

class CandidateTest extends Specification {

    Candidate candidate1
    Candidate candidate2

    void setup() {
        candidate1 = new Candidate.Builder()
                .firstName("John")
                .lastName("Doe")
                .loginDetails(new LoginDetails("email", "password"))
                .birthDate(LocalDate.parse("1990-01-01"))
                .description("Description")
                .cpf("12345678901")
                .education("Education")
                .addressId(1)
                .build()

        candidate2 = new Candidate.Builder()
                .firstName("Marcus") // Different first name
                .lastName("Doe")
                .loginDetails(new LoginDetails("email", "password"))
                .birthDate(LocalDate.parse("1990-01-01"))
                .description("Description")
                .cpf("12345678901")
                .education("Education")
                .addressId(1)
                .build()
    }


    def "Candidate builder returns new instance"() {
        given:
        Candidate candidate = new Candidate.Builder()
                .firstName("John")
                .lastName("Doe")
                .loginDetails(new LoginDetails("email", "password"))
                .birthDate(LocalDate.parse("1990-01-01"))
                .description("Description")
                .cpf("12345678901")
                .education("Education")
                .addressId(1)
                .build()

        expect:
        candidate != null
        candidate.firstName == "John"
        candidate.lastName == "Doe"
        candidate.loginDetails.email == "email"
        candidate.loginDetails.password == "password"
        candidate.birthDate == LocalDate.of(1990, 1, 1)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()
        candidate.description == "Description"
        candidate.cpf == "12345678901"
        candidate.education == "Education"
        candidate.addressId == 1
    }

    def "Builder throws exception when first name is not set"() {
        when:
        new Candidate.Builder()
                .lastName("Doe")
                .loginDetails(new LoginDetails("email", "password"))
                .birthDate(LocalDate.parse("1990-01-01"))
                .description("Description")
                .cpf("12345678901")
                .education("Education")
                .addressId(1)
                .build()

        then:
        thrown(FieldNotSetException)
    }

    def "Candidate equals method returns true"() {
        given:
        candidate2.firstName = candidate1.firstName // equalize first name

        expect:
        candidate1 == candidate2
    }

    def "Candidate equals method returns false"() {
        expect:
        candidate1 != candidate2
    }

    def "Candidate hashcode method returns same value"() {
        given:
        candidate2.firstName = candidate1.firstName // equalize first name

        expect:
        candidate1.hashCode() == candidate2.hashCode()
    }

    def "Candidate hashcode method returns different value"() {
        expect:
        candidate1.hashCode() != candidate2.hashCode()
    }
}
