package application.config.database

import application.config.Env
import application.config.database.interfaces.DBConnection

class DBConnectionFactory {

    static DBConnection createPostgresConnection(Env env) {
        return new PostgresConnection(env)
    }

    static DBConnection createPostgresCustomConnection(String url, String user, String password, String driver) {
        return new PostgresCustomConnection.Builder()
                .url(url)
                .user(user)
                .password(password)
                .driver(driver)
                .build()
    }
}
