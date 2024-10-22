package main.database

import spock.lang.Specification

import java.sql.SQLException

class DBDriverTest extends Specification {

    def "checkIfDriverIsLoaded throws IllegalArgumentException for null param"() {
        when:
        DBDriver.checkIfDriverIsLoaded(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "checkIfDriverIsLoaded throws SQLException when class does not exists"() {
        given:
        String driverClassName = "driverClassNameThatDoesNotExist" // Dummy class that does not exists

        when:
        DBDriver.checkIfDriverIsLoaded(driverClassName)

        then:
        thrown(SQLException)
    }

    def "checkIfDriverIsLoaded works with correct driver"() {
        given:
        String driverClassName = "java.time.Instant" // Dummy class that exists

        when:
        DBDriver.checkIfDriverIsLoaded(driverClassName)

        then:
        noExceptionThrown()
    }
}
