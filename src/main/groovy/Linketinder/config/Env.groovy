package Linketinder.config

import org.yaml.snakeyaml.Yaml

import java.time.ZoneId

class Env {
    static final String DB_URL
    static final String DB_USER
    static final String DB_PASSWORD
    static final String DB_DRIVER
    static final String DB_SCHEMAS
    static final String DB_LOCATIONS

    static final ZoneId TIMEZONE

    static {
        Yaml yaml = new Yaml()
        InputStream inputStream = Env.class.
                getClassLoader().
                getResourceAsStream("application.yml")

        if (inputStream == null) {
            DB_URL = null
            DB_USER = null
            DB_PASSWORD = null
            DB_DRIVER = null
            DB_SCHEMAS = null
            DB_LOCATIONS = null
            TIMEZONE = null

            throw new FileNotFoundException("application.yml not found in classpath")
        }

        Map<String, Map<String, String>> config = yaml.load(inputStream)

        DB_URL = config.get("db").get("url")
        DB_USER = config.get("db").get("user")
        DB_PASSWORD = config.get("db").get("password")
        DB_DRIVER = config.get("db").get("driver")
        DB_SCHEMAS = config.get("db").get("schemas")
        DB_LOCATIONS = config.get("db").get("locations")

        TIMEZONE = ZoneId.of(config.get("timezone").toString())
    }
}
