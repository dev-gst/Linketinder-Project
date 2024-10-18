package linketinder.util.config

import linketinder.util.parser.yaml.YamlParser

class ConfigLoader {

    private YamlParser yamlParser
    private Map<String, Map<String, String>> configs

    ConfigLoader(YamlParser yamlParser) {
        this.yamlParser = yamlParser
    }

    String getConfig(String firstKey, String secondKey) {
        if (configs == null) {
            throw new IllegalStateException("Configs have not been loaded")
        }

        return configs.get(firstKey).get(secondKey)
    }

    void loadConfigs(String yamlPath) {
        configs = yamlParser.parse(yamlPath)
    }
}
