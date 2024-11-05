package main.database

import main.database.interfaces.DBConnection
import main.util.config.Env

import java.sql.Connection

class DBConnectionManager {

    private static DBConnectionManager instance

    private DBConnection dbConnection

    private DBConnectionManager(Env env) {
        dbConnection = DBConnectionFactory.createPostgresConnection(env)
    }

    static DBConnectionManager getInstance(Env env) {
        if (instance == null) {
            instance = new DBConnectionManager(env)
        }

        return instance
    }

    Connection getConnection() {
        return dbConnection.getConnection()
    }

    void closeConnection() {
        dbConnection.closeConnection()
    }
}
