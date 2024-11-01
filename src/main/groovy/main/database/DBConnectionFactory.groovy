package main.database

import main.database.interfaces.DBConnection
import main.util.config.Env

class DBConnectionFactory {

    static DBConnection createPostgresConnection(Env env) {
        return new PostgresConnection(env)
    }
}
