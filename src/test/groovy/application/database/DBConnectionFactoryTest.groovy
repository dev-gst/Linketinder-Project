package application.database

import application.config.Env
import application.config.database.DBConnectionFactory
import spock.lang.Specification

class DBConnectionFactoryTest extends Specification {

    Env env

    void setup() {
        env = Mock(Env.class)
    }


    def "create postgres connection returns a new PostgresConnection instance"() {
        when:
        def connection = DBConnectionFactory.createPostgresConnection(env)

        then:
        connection != null
    }
}
