package Linketinder.database

import Linketinder.config.Env

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DBConnectionManager {
    private static final DBConnectionManager instance
    private Connection conn

    static {
        instance = new DBConnectionManager()
    }

    private DBConnectionManager() {
        try {
            Class.forName("org.postgresql.Driver")
        } catch (ClassNotFoundException e) {

            throw new ExceptionInInitializerError("Failed to initialize DBConnectionManager: " + e.getMessage())
        }

        connect()
    }

    private void connect() {
        try {
            this.conn =  DriverManager.getConnection(
                    Env.DB_URL,
                    Env.DB_USER,
                    Env.DB_PASSWORD
            )
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage())
            e.printStackTrace()

            throw new RuntimeException("Failed to connect to database", e)
        }
    }

    static DBConnectionManager getInstance() {

        return instance
    }

    void connectionClose() {
        if (this.conn != null && !this.conn.isClosed()) {
            try {
                this.conn.close()
            } catch (SQLException e) {
                throw new RuntimeException("Failed to close connection on Database", e)
            }
        }
    }

    Connection getConnection() {
        if (this.conn == null || this.conn.isClosed()) {
            connect()
        }

        return conn
    }
}
