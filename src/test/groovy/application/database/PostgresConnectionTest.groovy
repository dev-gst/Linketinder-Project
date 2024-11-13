package application.database

import application.config.Env
import application.config.database.DBDriver
import application.config.database.PostgresConnection
import spock.lang.Specification

import java.sql.Connection
import java.sql.DriverManager

class PostgresConnectionTest extends Specification {

    Connection mockedConnection1
    Connection mockedConnection2
    Env mockedEnv

    PostgresConnection postgresConnection

    void setup() {
        mockedConnection1 = Mock(Connection.class)
        mockedConnection2 = Mock(Connection.class)

        GroovyMock(DriverManager, global: true)
        GroovyMock(DBDriver, global: true)
        DriverManager.getConnection("", "", "") >> mockedConnection1 >> mockedConnection2

        mockedEnv = Mock(Env)
        mockedEnv.getDbUrl() >> ""
        mockedEnv.getDbUser() >> ""
        mockedEnv.getDbPassword() >> ""
        postgresConnection = new PostgresConnection(mockedEnv)
    }

    def "getConnection returns connection"() {
        when:
        Connection connection = postgresConnection.getConnection()

        then:
        connection != null
    }

    def "getConnection returns same connection"() {
        when:
        Connection connection1 = postgresConnection.getConnection()
        Connection connection2 = postgresConnection.getConnection()

        then:
        connection1 == connection2
    }

    def "closeConnection closes the connection"() {
        when:
        postgresConnection.getConnection()
        postgresConnection.closeConnection()

        then:
        1 * mockedConnection1.close()
    }

    def "getConnection returns new connection if closed"() {
        given:
        mockedConnection1.isClosed() >> false >> true

        when:
        Connection conn1 = postgresConnection.getConnection()
        postgresConnection.closeConnection()
        Connection conn2 = postgresConnection.getConnection()

        then:
        conn1 != conn2
        1 * mockedConnection1.close()
    }
}
