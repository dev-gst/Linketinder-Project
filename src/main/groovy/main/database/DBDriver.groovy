package main.database

import java.sql.SQLException

class DBDriver {

    static void checkIfDriverIsLoaded(String driverClassName) throws SQLException {
        try {
            loadDriverClass(driverClassName)
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found", e)
        }
    }

    private static void loadDriverClass(String driverClassName) throws ClassNotFoundException {
        Class.forName(driverClassName)
    }
}
