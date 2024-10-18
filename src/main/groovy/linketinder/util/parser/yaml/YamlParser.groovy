package linketinder.util.parser.yaml

interface YamlParser {
    Map<String, Map<String, String>> parse(String path)
}