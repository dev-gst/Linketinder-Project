package application.config

import application.utils.parsers.yaml.YamlParser

class ConfigLoader {

    private YamlParser yamlParser
    private Map<String, Map<String, String>> configs

    ConfigLoader(YamlParser yamlParser) {
        if (yamlParser == null) {
            throw new IllegalArgumentException("YamlParser instance cannot be null")
        }

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
        configs = yamlParser.parse(yamlPath)
    }
}
