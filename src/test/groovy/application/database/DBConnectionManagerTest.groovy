package application.database

import application.config.Env
import application.config.database.DBConnectionFactory
import application.config.database.DBConnectionManager
import application.config.database.interfaces.DBConnection
import spock.lang.Specification

import java.sql.Connection

class DBConnectionManagerTest extends Specification {

    Env env
    DBConnection dbConnection

    void setup() {
        env = Mock(Env.class)
        dbConnection = Mock(DBConnection.class)
        GroovyMock(DBConnectionFactory, global: true)
        DBConnectionFactory.createPostgresConnection(env) >> dbConnection
    }

    void "get instance returns a new DBConnectionManager instance"() {
        given:
        DBConnectionManager instance = DBConnectionManager.getInstance(env)

        expect:
        instance != null
    }

    void "get connection returns a new connection"() {
        given:
        Connection mockedConnection = Mock(Connection.class)
        DBConnectionManager instance = DBConnectionManager.getInstance(env)
        dbConnection.getConnection() >> mockedConnection

        when:
        Connection conn = instance.getConnection()

        then:
        conn == mockedConnection
        1 * dbConnection.getConnection() >> mockedConnection
    }
}