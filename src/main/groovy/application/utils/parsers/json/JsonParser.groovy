package application.utils.parsers.json

interface JsonParser {

    String toJson(Object object)

    <T> T fromJson(String json, Class<T> clazz)
}