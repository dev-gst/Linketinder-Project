package main.util.parser.yaml

import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class SnakeYamlParserTest extends Specification {

    def "parse should throw an exception when path is null"() {
        given:
        def yamlParser = new SnakeYamlParser(new Yaml())

        when:
        yamlParser.parse(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "parse should throw an exception when path is empty"() {
        given:
        def yamlParser = new SnakeYamlParser(new Yaml())

        when:
        yamlParser.parse("")

        then:
        thrown(IllegalArgumentException)
    }

    def "parse should throw an exception when path is blank"() {
        given:
        def yamlParser = new SnakeYamlParser(new Yaml())

        when:
        yamlParser.parse("\n")

        then:
        thrown(IllegalArgumentException)
    }

    def "parse should throw an exception when path is invalid"() {
        given:
        def yamlParser = new SnakeYamlParser(new Yaml())

        when:
        yamlParser.parse("invalid")

        then:
        thrown(FileNotFoundException)
    }

    def "parse should return a map when path is valid"() {
        given:
        def yamlParser = new SnakeYamlParser(new Yaml())

        when:
        def result = yamlParser.parse("src/test/resources/test.yaml")

        then:
        result != null
        result.get("db").get("url") == "jdbc:postgresql://localhost:5432/test"
        result.get("db").get("user") == "testUsername"
        result.get("db").get("password") == "testPassword"
        result.get("db").get("driver") == "testDriver"
        result.get("db").get("schemas") == "testSchema"
        result.get("flyway").get("location") == "testLocation"
        result.get("timezone").get("current") == "testTimezone"
    }
}
