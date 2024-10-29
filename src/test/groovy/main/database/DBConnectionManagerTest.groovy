package main.database

import main.util.config.Env
import spock.lang.Specification

import java.sql.Connection
import java.sql.DriverManager

class DBConnectionManagerTest extends Specification {

    Connection mockedConnection
    Env mockedEnv
    DBConnectionManager dbConnectionManager

    def setup() {
        mockedConnection = Mock()
        GroovyMock(DBDriver, global: true)
        DBDriver.checkIfDriverIsLoaded(null)
        GroovyMock(DriverManager, global: true)
        DriverManager.getConnection("", "", "") >> mockedConnection
        mockedEnv = Mock(Env)
        mockedEnv.getDbUrl() >> ""
        mockedEnv.getDbUser() >> ""
        mockedEnv.getDbPassword() >> ""
        dbConnectionManager = new DBConnectionManager(mockedEnv)
    }

    def "getConnection returns connection"() {
        when:
        Connection connection = dbConnectionManager.getConnection()

        then:
        connection != null
    }

    def "getConnection returns same connection"() {
        when:
        Connection connection1 = dbConnectionManager.getConnection()
        Connection connection2 = dbConnectionManager.getConnection()

        then:
        connection1 == connection2
    }

    def "closeConnection closes the connection"() {
        when:
        dbConnectionManager.getConnection()
        dbConnectionManager.closeConnection()

        then:
        1 * mockedConnection.close()
    }

    def "getConnection returns new connection if closed"() {

        mockedConnection.isClosed() >>> [false, true]

        when:
        dbConnectionManager.getConnection()
        dbConnectionManager.closeConnection()
        dbConnectionManager.getConnection()

        then:
        2 * DBDriver.checkIfDriverIsLoaded(null)
    }
}