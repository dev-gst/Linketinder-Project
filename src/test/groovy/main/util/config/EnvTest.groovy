package main.util.config

import spock.lang.Specification

import java.time.ZoneId

class EnvTest extends Specification {

    def setup() {
        Env.resetInstance()
    }

    def "allSettingsAreLoadedCorrectly"() {
        given:
        ConfigLoader configLoader = Mock(ConfigLoader)

        when:
        configLoader.getConfig("db", "url") >> "url"
        configLoader.getConfig("db", "user") >> "user"
        configLoader.getConfig("db", "password") >> "password"
        configLoader.getConfig("db", "driver") >> "driver"
        configLoader.getConfig("db", "schemas") >> "schemas"
        configLoader.getConfig("flyway", "location") >> "location"
        configLoader.getConfig("timezone", "current") >> "UTC"

        Env env = Env.getInstance(configLoader)

        then:
        env.DB_URL == "url"
        env.DB_USER == "user"
        env.DB_PASSWORD == "password"
        env.DB_DRIVER == "driver"
        env.DB_SCHEMAS == "schemas"
        env.FLYWAY_LOCATION == "location"
        env.TIMEZONE == ZoneId.of("UTC")
    }

    def "constructorThrowsExceptionWithNullValue"() {
        given:
        ConfigLoader configLoader = null

        when:
        configLoader.getConfig("db", "url") >> "url"
        configLoader.getConfig("db", "user") >> "user"
        configLoader.getConfig("db", "password") >> "password"
        configLoader.getConfig("db", "driver") >> "driver"
        configLoader.getConfig("db", "schemas") >> "schemas"
        configLoader.getConfig("flyway", "location") >> "location"
        configLoader.getConfig("timezone", "current") >> "UTC"

        Env.getInstance(configLoader)

        then:
        thrown(IllegalArgumentException)
    }
}
