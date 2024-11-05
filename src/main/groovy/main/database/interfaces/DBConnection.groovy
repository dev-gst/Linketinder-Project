package main.database.interfaces

import java.sql.Connection

interface DBConnection {

    Connection getConnection()

    void closeConnection()
}