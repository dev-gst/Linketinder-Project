package application.config.database

import application.config.database.interfaces.DBConnection
import application.utils.exceptions.FieldNotSetException
import application.utils.validation.ParamValidation

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class PostgresCustomConnection implements DBConnection {

    private String url
    private String user
    private String password
    private String driver

    private Connection conn

    private PostgresCustomConnection(Builder builder) {
        this.url = builder.url
        this.user = builder.user
        this.password = builder.password
        this.driver = builder.driver
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
        DBDriver.checkIfDriverIsLoaded(driver)

        return DriverManager.getConnection(url, user, password)
    }

    @Override
    void closeConnection() {
        if (this.conn != null && !this.conn.isClosed()) {
            this.conn.close()
        }
    }

    static class Builder {

        String url
        String user
        String password
        String driver

        private boolean urlSet = false
        private boolean userSet = false
        private boolean passwordSet = false
        private boolean driverSet = false

        Builder url(String url) {
            ParamValidation.requireNonBlank(url, "url cannot be blank")

            this.url = url
            this.urlSet = true

            return this
        }

        Builder user(String user) {
            ParamValidation.requireNonBlank(user, "user cannot be blank")

            this.user = user
            this.userSet = true

            return this
        }

        Builder password(String password) {
            ParamValidation.requireNonBlank(password, "password cannot be blank")

            this.password = password
            this.passwordSet = true

            return this
        }

        Builder driver(String driver) {
            ParamValidation.requireNonBlank(driver, "driver cannot be blank")

            this.driver = driver
            this.driverSet = true

            return this
        }

        PostgresCustomConnection build() {
            validate()

            return new PostgresCustomConnection(this)
        }

        private validate() {
            if (!urlSet) throw new FieldNotSetException("url must be set")
            if (!userSet) throw new FieldNotSetException("user must be set")
            if (!passwordSet) throw new FieldNotSetException("password must be set")
            if (!driverSet) throw new FieldNotSetException("driver must be set")
        }
    }
}
