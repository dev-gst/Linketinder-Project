package application.utils.parsers.json

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class JacksonJsonParserTest extends Specification {

    def "toJson converts object to JSON string"() {
        given:
        ObjectMapper objectMapper = new ObjectMapper()
        JacksonJsonParser parser = new JacksonJsonParser(objectMapper)
        Object object = [key: "value"]

        when:
        String json = parser.toJson(object)

        then:
        json == '{"key":"value"}'
    }

    def "toJson throws exception when object is null"() {
        given:
        ObjectMapper objectMapper = new ObjectMapper()
        JacksonJsonParser parser = new JacksonJsonParser(objectMapper)

        when:
        parser.toJson(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "constructor throws exception when ObjectMapper is null"() {
        when:
        new JacksonJsonParser(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "fromJson converts JSON string to object"() {
        given:
        ObjectMapper objectMapper = new ObjectMapper()
        JacksonJsonParser parser = new JacksonJsonParser(objectMapper)
        String json = '{"key":"value"}'
        Class<Map> clazz = Map

        when:
        Map result = parser.fromJson(json, clazz)

        then:
        result == [key: "value"]
    }

    def "fromJson throws exception when JSON string is null"() {
        given:
        ObjectMapper objectMapper = new ObjectMapper()
        JacksonJsonParser parser = new JacksonJsonParser(objectMapper)
        Class<Map> clazz = Map

        when:
        parser.fromJson(null, clazz)

        then:
        thrown(IllegalArgumentException)
    }

    def "fromJson throws exception when class type is null"() {
        given:
        ObjectMapper objectMapper = new ObjectMapper()
        JacksonJsonParser parser = new JacksonJsonParser(objectMapper)
        String json = '{"key":"value"}'

        when:
        parser.fromJson(json, null)

        then:
        thrown(IllegalArgumentException)
    }

    def "fromJson throws exception for invalid JSON string"() {
        given:
        ObjectMapper objectMapper = new ObjectMapper()
        JacksonJsonParser parser = new JacksonJsonParser(objectMapper)
        String json = 'invalid json'
        Class<Map> clazz = Map

        when:
        parser.fromJson(json, clazz)

        then:
        thrown(JsonParseException)
    }
}