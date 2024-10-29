package main.database

import main.util.config.Env
import main.util.exception.ParamValidation

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DBConnectionManager {

    private Connection conn

    final Env env

    DBConnectionManager(Env env) {
        ParamValidation.requireNonNull(env, "Env cannot be null")
        this.env = env
    }

    Connection getConnection() {
        if (this.conn == null || this.conn.isClosed()) {
            DBDriver.checkIfDriverIsLoaded(env.getDbDriver())
            connect()
        }

        return conn
    }

    private void connect() {
        try {
            this.conn = buildConnection()
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database", e)
        }
    }

    private Connection buildConnection() {
        return DriverManager.getConnection(
                env.getDbUrl(),
                env.getDbUser(),
                env.getDbPassword()
        )
    }

    void closeConnection() {
        if (this.conn != null && !this.conn.isClosed()) {
            this.conn.close()
        }
    }
}
