package linketinder.util.config

import java.time.ZoneId

class Env {

    private static instance
    private static final String applicationYml = "application.yaml"

    final String DB_URL
    final String DB_USER
    final String DB_PASSWORD
    final String DB_DRIVER
    final String DB_SCHEMAS
    final String FLYWAY_LOCATION
    final ZoneId TIMEZONE

    private Env(ConfigLoader configLoader) {
        if (configLoader == null) {
            this.DB_URL = null
            this.DB_USER = null
            this.DB_PASSWORD = null
            this.DB_DRIVER = null
            this.DB_SCHEMAS = null
            this.FLYWAY_LOCATION = null
            this.TIMEZONE = null

            throw new IllegalArgumentException("ConfigLoader is null")
        }

        configLoader.loadConfigs(applicationYml)

        this.DB_URL = configLoader.getConfig("db", "url")
        this.DB_USER = configLoader.getConfig("db", "user")
        this.DB_PASSWORD = configLoader.getConfig("db", "password")
        this.DB_DRIVER = configLoader.getConfig("db", "driver")
        this.DB_SCHEMAS = configLoader.getConfig("db", "schemas")
        this.FLYWAY_LOCATION = configLoader.getConfig("flyway", "location")
        this.TIMEZONE = ZoneId.of(configLoader.getConfig("timezone", "current"))
    }

    static Env getInstance(ConfigLoader configLoader) {
        if (instance == null) {
            instance = new Env(configLoader)
        }

        return instance
    }

    static resetInstance() {
        instance = null
    }
}
