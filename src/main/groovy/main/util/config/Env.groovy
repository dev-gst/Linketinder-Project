package main.util.config

import java.time.ZoneId

class Env {
    private static final String applicationYml = "application.yaml"

    private final String DB_URL
    private final String DB_USER
    private final String DB_PASSWORD
    private final String DB_DRIVER
    private final String DB_SCHEMAS
    private final String FLYWAY_LOCATION
    private final ZoneId TIMEZONE

    Env(ConfigLoader configLoader) {
        Objects.requireNonNull(configLoader, "ConfigLoader cannot be null")
        configLoader.loadConfigs(applicationYml)

        this.DB_URL = configLoader.getConfig("db", "url")
        this.DB_USER = configLoader.getConfig("db", "user")
        this.DB_PASSWORD = configLoader.getConfig("db", "password")
        this.DB_DRIVER = configLoader.getConfig("db", "driver")
        this.DB_SCHEMAS = configLoader.getConfig("db", "schemas")
        this.FLYWAY_LOCATION = configLoader.getConfig("flyway", "location")
        this.TIMEZONE = ZoneId.of(configLoader.getConfig("timezone", "current"))
    }

    String getDbUrl() {
        return DB_URL
    }

    String getDbUser() {
        return DB_USER
    }

    String getDbPassword() {
        return DB_PASSWORD
    }

    String getDbDriver() {
        return DB_DRIVER
    }

    String getDbSchemas() {
        return DB_SCHEMAS
    }

    String getFlywayLocation() {
        return FLYWAY_LOCATION
    }

    ZoneId getTimezone() {
        return TIMEZONE
    }
}
