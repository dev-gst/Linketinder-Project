package application.utils.parsers.json

import application.utils.validation.ParamValidation
import com.fasterxml.jackson.databind.ObjectMapper

class JacksonJsonParser implements JsonParser {

    private ObjectMapper objectMapper

    JacksonJsonParser(ObjectMapper objectMapper) {
        ParamValidation.requireNonNull(objectMapper, "ObjectMapper cannot not be null")

        this.objectMapper = objectMapper
    }

    @Override
    String toJson(Object object) {
        ParamValidation.requireNonNull(object, "Object cannot not be null")

        return objectMapper.writeValueAsString(object)
    }

    <T> T fromJson(String json, Class<T> clazz) {
        ParamValidation.requireNonNull(json, "JSON string cannot not be null")
        ParamValidation.requireNonNull(clazz, "Class cannot not be null")

        return objectMapper.readValue(json, clazz)
    }
}
