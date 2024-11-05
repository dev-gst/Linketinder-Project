package main.database

import main.database.interfaces.DBConnection
import main.util.config.Env

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

    @Override
    void closeConnection() {
        if (this.conn != null && !this.conn.isClosed()) {
            this.conn.close()
        }
    }
}
