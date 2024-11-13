package application.util.exception

import application.utils.validation.ParamValidation
import spock.lang.Specification

class ParamValidationTest extends Specification {

    def "requireNonNull should throw IllegalArgumentException when object is null"() {
        when:
        ParamValidation.requireNonNull(null, "Object cannot be null")

        then:
        thrown(IllegalArgumentException)
    }

    def "requireNonNull should not throw IllegalArgumentException when object is not null"() {
        when:
        ParamValidation.requireNonNull("Object", "Object cannot be null")

        then:
        notThrown(IllegalArgumentException)
    }

    def "requireNonBlank should throw IllegalArgumentException when string is null"() {
        when:
        ParamValidation.requireNonBlank(null, "String cannot be null")

        then:
        thrown(IllegalArgumentException)
    }

    def "requireNonBlank should throw IllegalArgumentException when string is blank"() {
        when:
        ParamValidation.requireNonBlank("", "String cannot be blank")

        then:
        thrown(IllegalArgumentException)
    }

    def "requireNonBlank should not throw IllegalArgumentException when string is not blank"() {
        when:
        ParamValidation.requireNonBlank("String", "String cannot be blank")

        then:
        notThrown(IllegalArgumentException)
    }

    def "requirePositive should throw IllegalArgumentException when number is null"() {
        when:
        ParamValidation.requirePositive(null, "Number cannot be null")

        then:
        thrown(IllegalArgumentException)
    }

    def "requirePositive should throw IllegalArgumentException when number is negative"() {
        when:
        ParamValidation.requirePositive(-1, "Number must be positive")

        then:
        thrown(IllegalArgumentException)
    }

    def "requirePositive should not throw IllegalArgumentException when number is positive"() {
        when:
        ParamValidation.requirePositive(1, "Number must be positive")

        then:
        notThrown(IllegalArgumentException)
    }

    def "requireNonEmpty should throw IllegalArgumentException when collection is null"() {
        when:
        ParamValidation.requireNonEmpty(null, "Collection cannot be null")

        then:
        thrown(IllegalArgumentException)
    }

    def "requireNonEmpty should throw IllegalArgumentException when collection is empty"() {
        when:
        ParamValidation.requireNonEmpty([], "Collection cannot be empty")

        then:
        thrown(IllegalArgumentException)
    }
}
