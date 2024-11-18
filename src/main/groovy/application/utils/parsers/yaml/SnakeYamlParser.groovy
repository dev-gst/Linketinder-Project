package application.utils.parsers.yaml

import application.utils.validation.ParamValidation
import org.yaml.snakeyaml.Yaml

class SnakeYamlParser implements YamlParser {

    Yaml yaml

    SnakeYamlParser(Yaml yaml) {
        ParamValidation.requireNonNull(yaml, "Yaml cannot be null")

        this.yaml = yaml
    }

    @Override
    Map<String, Map<String, String>> parse(String path) {
        ParamValidation.requireNonNull(path, "Path cannot be null")
        if (path.isBlank()) throw new IllegalArgumentException("Path cannot be blank")

        InputStream inputStream = getInputStream(path)

        return yaml.load(inputStream)
    }

    private static InputStream getInputStream(String path) {
        return new FileInputStream(path)
    }
}
