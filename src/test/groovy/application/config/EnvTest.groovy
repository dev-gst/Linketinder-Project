package application.config

import spock.lang.Specification

import java.time.ZoneId

class EnvTest extends Specification {

    def "all settings are loaded correctly"() {
        given:
        ConfigLoader configLoader = Mock(ConfigLoader)
        configLoader.getConfig("db", "name") >> "name"
        configLoader.getConfig("db", "url") >> "url"
        configLoader.getConfig("db", "user") >> "user"
        configLoader.getConfig("db", "password") >> "password"
        configLoader.getConfig("db", "driver") >> "driver"
        configLoader.getConfig("db", "schemas") >> "schemas"
        configLoader.getConfig("flyway", "location") >> "location"
        configLoader.getConfig("timezone", "current") >> "UTC"

        when:
        Env env = new Env(configLoader)

        then:
        env.getDbName() == "name"
        env.getDbUrl() == "url"
        env.getDbUser() == "user"
        env.getDbPassword() == "password"
        env.getDbDriver() == "driver"
        env.getDbSchemas() == "schemas"
        env.getFlywayLocation() == "location"
        env.getTimezone() == ZoneId.of("UTC")
    }

    @SuppressWarnings('GroovyResultOfObjectAllocationIgnored')
    def "constructor throws IllegalArgumentException with null parameter"() {
        given:
        ConfigLoader configLoader = null

        when:
        new Env(configLoader)

        then:
        thrown(IllegalArgumentException)
    }
}
