package application.ui.helpers

import spock.lang.Specification

import java.time.Instant
import java.time.LocalDate

class UserInputCollectorTest extends Specification {

    def "getChoice valid input returns choice"() {
        given:
        Scanner scanner = new Scanner("2\n")

        when:
        int result = UserInputCollector.getChoice(3, scanner)

        then:
        result == 2
    }

    def "getChoice invalid input prompts again"() {
        given:
        Scanner scanner = new Scanner("a\n2\n")

        when:
        int result = UserInputCollector.getChoice(3, scanner)

        then:
        result == 2
    }

    def "getChoice out of range input prompts again"() {
        given:
        Scanner scanner = new Scanner("5\n2\n")

        when:
        int result = UserInputCollector.getChoice(3, scanner)

        then:
        result == 2
    }

    def "getString valid input returns string"() {
        given:
        Scanner scanner = new Scanner("valid input\n")

        when:
        String result = UserInputCollector.getString(scanner)

        then:
        result == "valid input"
    }

    def "getString blank input prompts again"() {
        given:
        Scanner scanner = new Scanner("\nvalid input\n")

        when:
        String result = UserInputCollector.getString(scanner)

        then:
        result == "valid input"
    }

    def "getInstant valid input returns instant"() {
        given:
        Scanner scanner = new Scanner("2023-10-10\n")

        when:
        Instant result = UserInputCollector.getInstant(scanner)

        then:
        result == Instant.parse("2023-10-10T00:00:00Z")
    }

    def "getInstant invalid input prompts again"() {
        given:
        Scanner scanner = new Scanner("invalid\n2023-10-10\n")

        when:
        Instant result = UserInputCollector.getInstant(scanner)

        then:
        result == Instant.parse("2023-10-10T00:00:00Z")
    }

    def "getLocalDate returns local date"() {
        given:
        Scanner scanner = new Scanner("2023-10-10\n")

        when:
        LocalDate result = UserInputCollector.getLocalDate(scanner)

        then:
        result == LocalDate.parse("2023-10-10")
    }

    def "getLocalDate invalid input prompts again"() {
        given:
        Scanner scanner = new Scanner("invalid\n2023-10-10\n")

        when:
        LocalDate result = UserInputCollector.getLocalDate(scanner)

        then:
        result == LocalDate.parse("2023-10-10")
    }

    def "getBoolean valid input returns boolean"() {
        given:
        Scanner scanner = new Scanner("s\n")

        when:
        boolean result = UserInputCollector.getBoolean(scanner)

        then:
        result
    }

    def "getBoolean any input other than s/y returns false"() {
        given:
        Scanner scanner = new Scanner("invalid\ns\n")

        when:
        boolean result = UserInputCollector.getBoolean(scanner)

        then:
        !result
    }

    def "getInteger valid input returns integer"() {
        given:
        def scanner = new Scanner("42\n")

        when:
        def result = UserInputCollector.getInteger(scanner)

        then:
        result == 42
    }

    def "getInteger invalid input prompts again"() {
        given:
        Scanner scanner = new Scanner("invalid\n42\n")

        when:
        int result = UserInputCollector.getInteger(scanner)

        then:
        result == 42
    }
}
