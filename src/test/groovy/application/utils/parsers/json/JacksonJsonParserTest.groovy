package application.utils.parsers.json

import application.models.dtos.request.CandidateDTO
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

    def "toJson parses sets of objects"() {
        given:
        ObjectMapper objectMapper = new ObjectMapper()
        JacksonJsonParser parser = new JacksonJsonParser(objectMapper)
        Object object = [key: "value"]
        Object object2 = [key: "value2"]

        Set<Object> objects = [object, object2]

        when:
        String json = parser.toJson(objects)

        then:
        json == '[{"key":"value"},{"key":"value2"}]'
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

    def "fromJson serializes CandidateDTO"() {
        given:

        String candidateJson = """{
            "firstName": "marcos",
            "lastName": "douglas",
            "loginDetailsDTO": {
                "email": "blabla@email.com",
                "password": "blabla"
            },
            "cpf": "24324234234",
            "birthDate": "1960-01-01T03:00:00Z",
            "description": "blablabla",
            "education": "Engineer",
            "addressId": 4
        }"""

        ObjectMapper objectMapper = new ObjectMapper()
        JacksonJsonParser parser = new JacksonJsonParser(objectMapper)

        when:
        CandidateDTO candidateDTO = parser.fromJson(candidateJson, CandidateDTO)

        then:
        candidateDTO.firstName == "marcos"
    }
}