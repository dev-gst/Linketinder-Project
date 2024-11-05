package main.util.config

import spock.lang.Specification

import java.time.ZoneId

class EnvTest extends Specification {

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

        Env env = new Env(configLoader)

        then:
        env.getDbUrl() == "url"
        env.getDbUser() == "user"
        env.getDbPassword() == "password"
        env.getDbDriver() == "driver"
        env.getDbSchemas() == "schemas"
        env.getFlywayLocation() == "location"
        env.getTimezone() == ZoneId.of("UTC")
    }

    @SuppressWarnings('GroovyResultOfObjectAllocationIgnored')
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

        new Env(configLoader)

        then:
        thrown(NullPointerException)
    }
}