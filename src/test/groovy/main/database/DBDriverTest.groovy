package main.database

import spock.lang.Specification

import java.sql.SQLException

class DBDriverTest extends Specification {

    def "checkIfDriverIsLoadedThrowsSQLException"() {
        given:
        String driverClassName = "driverClassNameThatDoesNotExist" // Dummy class that does not exists

        when:
        DBDriver.checkIfDriverIsLoaded(driverClassName)

        then:
        thrown(SQLException)
    }

    def "checkIfDriverIsLoadedWorksWithCorrectDriver"() {
        given:
        String driverClassName = "java.time.Instant" // Dummy class that exists

        when:
        DBDriver.checkIfDriverIsLoaded(driverClassName)

        then:
        noExceptionThrown()
    }
}
