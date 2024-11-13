package application.utils.parsers.yaml

interface YamlParser {
    Map<String, Map<String, String>> parse(String path)
}