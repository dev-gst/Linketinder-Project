package main.util.exception

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
}
