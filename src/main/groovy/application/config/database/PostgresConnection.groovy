package application.config.database

import application.config.Env
import application.config.database.interfaces.DBConnection

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class PostgresConnection implements DBConnection {

    private Connection conn

    final Env env

    PostgresConnection(Env env) {
        this.env = env
    }

    @Override
    Connection getConnection() {
        connect()

        return this.conn
    }

    private void connect() {
        if (this.conn != null && !this.conn.isClosed()) {
            return
        }

        try {
            this.conn = buildConnection()
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database", e)
        }
    }

    private Connection buildConnection() {
        DBDriver.checkIfDriverIsLoaded(env.getDbDriver())

        return DriverManager.getConnection(env.getDbUrl(), env.getDbUser(), env.getDbPassword())
    }

    @Override
    void closeConnection() {
        if (this.conn != null && !this.conn.isClosed()) {
            this.conn.close()
        }
    }
}
