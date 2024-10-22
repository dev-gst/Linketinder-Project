package main.database

import main.util.exception.ParamValidation

import java.sql.SQLException

class DBDriver {

    static void checkIfDriverIsLoaded(String driverClassName) throws SQLException {
        ParamValidation.requireNonBlank(driverClassName, "Driver class name cannot be blank or null")
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
