package application.config.database

import application.config.Env
import application.config.database.interfaces.DBConnection

class DBConnectionFactory {

    static DBConnection createPostgresConnection(Env env) {
        return new PostgresConnection(env)
    }
}
