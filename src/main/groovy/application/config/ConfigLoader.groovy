package application.config

import application.utils.io.FileSearcher
import application.utils.parsers.yaml.YamlParser
import application.utils.validation.ParamValidation

class ConfigLoader {

    private YamlParser yamlParser
    private Map<String, Map<String, String>> configs

    ConfigLoader(YamlParser yamlParser) {
        ParamValidation.requireNonNull(yamlParser, "YamlParser cannot be null")

        this.yamlParser = yamlParser
    }

    String getConfig(String firstKey, String secondKey) {
        if (configs == null) {
            throw new IllegalStateException("Configs have not been loaded")
        }

        String config = configs.get(firstKey).get(secondKey)
        if (config == null) {
            throw new IllegalArgumentException("Config with key $firstKey.$secondKey not found")
        }

        return config
    }

    void loadConfigs(String yamlPath) {
        try {
            configs = yamlParser.parse(yamlPath)
        } catch (FileNotFoundException ignored) {
            String updatedFilePath = FileSearcher.search(yamlPath)
            configs = yamlParser.parse(updatedFilePath)
        }
    }
}
