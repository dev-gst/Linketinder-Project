package Linketinder.config

import org.yaml.snakeyaml.Yaml

class Env {
    static final String DB_URL
    static final String DB_USER
    static final String DB_PASSWORD
    static final String DB_DRIVER
    static final String DB_SCHEMAS
    static final String DB_LOCATIONS

    static {
        Yaml yaml = new Yaml()
        Map<String, Map<String, String>> config = yaml.load(
                new FileInputStream(
                        "src/main/resources/application.yml"
                )
        )

        DB_URL = config.get("db").get("url")
        DB_USER = config.get("db").get("user")
        DB_PASSWORD = config.get("db").get("password")
        DB_DRIVER = config.get("db").get("driver")
        DB_SCHEMAS = config.get("db").get("schemas")
        DB_LOCATIONS = config.get("db").get("locations")
    }
}
