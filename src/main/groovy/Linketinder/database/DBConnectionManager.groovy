package Linketinder.database

import Linketinder.config.Env

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DBConnectionManager {

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Env.DB_URL, Env.DB_USER, Env.DB_PASSWORD)
    }
}
