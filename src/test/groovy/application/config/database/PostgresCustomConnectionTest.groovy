package application.config.database

import spock.lang.Specification

import java.sql.Connection
import java.sql.DriverManager

class PostgresCustomConnectionTest extends Specification {

    Connection mockedConnection1
    Connection mockedConnection2
    String mockedUrl
    String mockedUser
    String mockedPassword
    String mockedDriver

    PostgresCustomConnection postgresCustomConnection

    void setup() {
        mockedConnection1 = Mock(Connection.class)
        mockedConnection2 = Mock(Connection.class)

        GroovyMock(DriverManager, global: true)
        GroovyMock(DBDriver, global: true)
        DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "test", "test") >> mockedConnection1 >> mockedConnection2

        mockedUrl = "jdbc:postgresql://localhost:5432/test"
        mockedUser = "test"
        mockedPassword = "test"
        mockedDriver = "org.postgresql.Driver"

        postgresCustomConnection = new PostgresCustomConnection.Builder()
                .url(mockedUrl)
                .user(mockedUser)
                .password(mockedPassword)
                .driver(mockedDriver)
                .build()
    }

    def "getConnection returns connection"() {
        when:
        Connection connection = postgresCustomConnection.getConnection()

        then:
        connection != null
    }

    def "getConnection returns same connection"() {
        when:
        Connection connection1 = postgresCustomConnection.getConnection()
        Connection connection2 = postgresCustomConnection.getConnection()

        then:
        connection1 == connection2
    }

    def "closeConnection closes the connection"() {
        when:
        postgresCustomConnection.getConnection()
        postgresCustomConnection.closeConnection()

        then:
        1 * mockedConnection1.close()
    }

    def "getConnection returns new connection if closed"() {
        given:
        mockedConnection1.isClosed() >> false >> true

        when:
        Connection conn1 = postgresCustomConnection.getConnection()
        postgresCustomConnection.closeConnection()
        Connection conn2 = postgresCustomConnection.getConnection()

        then:
        conn1 != conn2
        1 * mockedConnection1.close()
    }
}