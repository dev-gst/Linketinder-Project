package application.utils.parsers.json

import com.fasterxml.jackson.databind.ObjectMapper

class JsonParserFactory {

    static JsonParser createJacksonJsonParser() {
        return new JacksonJsonParser(new ObjectMapper())
    }
}
