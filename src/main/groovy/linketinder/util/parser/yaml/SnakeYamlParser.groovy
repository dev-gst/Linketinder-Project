package linketinder.util.parser.yaml

import org.yaml.snakeyaml.Yaml

class SnakeYamlParser implements YamlParser {

    Yaml yaml

    SnakeYamlParser(Yaml yaml) {
        if (yaml == null) {
            throw new IllegalArgumentException("Yaml instance cannot be null")
        }

        this.yaml = yaml
    }

    @Override
    Map<String, Map<String, String>> parse(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty")
        }

        InputStream inputStream = getInputStream(path)

        return yaml.load(inputStream)
    }

    private static InputStream getInputStream(String path) {
        return new FileInputStream(path)
    }
}
