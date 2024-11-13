package application.database

import application.config.database.DBDriver
import spock.lang.Specification

import java.sql.SQLException

class DBDriverTest extends Specification {

    def "Check if driver is loaded throws IllegalArgumentException for null param"() {
        when:
        DBDriver.checkIfDriverIsLoaded(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "Check if driver is loaded throws IllegalArgumentException for empty param"() {
        when:
        DBDriver.checkIfDriverIsLoaded("")

        then:
        thrown(IllegalArgumentException)
    }

    def "Check if driver is loaded throws SQLException when class does not exists"() {
        given:
        String driverClassName = "driverClassNameThatDoesNotExist" // Dummy class that does not exists

        when:
        DBDriver.checkIfDriverIsLoaded(driverClassName)

        then:
        thrown(SQLException)
    }

    def "Check if driver is loaded works with correct driver"() {
        given:
        String driverClassName = "java.time.Instant" // Dummy class that exists

        when:
        DBDriver.checkIfDriverIsLoaded(driverClassName)

        then:
        noExceptionThrown()
    }
}
