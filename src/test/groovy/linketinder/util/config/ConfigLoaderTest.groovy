package linketinder.util.config

import linketinder.util.parser.yaml.SnakeYamlParser
import linketinder.util.parser.yaml.YamlParser
import spock.lang.Specification

class ConfigLoaderTest extends Specification {

    def "constructorThrowsIllegalArgumentExceptionWhenYamlParserIsNull"() {
        when:
        new ConfigLoader(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "loadConfigsWorksCorrectly"() {
        given:
        YamlParser yamlParser = Mock(SnakeYamlParser)
        def configLoader = new ConfigLoader(yamlParser)

        when:
        configLoader.loadConfigs("path")

        then:
        1 * yamlParser.parse("path")
    }

    def "getConfigReturnsCorrectValue"() {
        given:
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>()
        Map<String, String> innerMap = new HashMap<String, String>()
        innerMap.put("secondKey", "value")
        map.put("firstKey", innerMap)

        YamlParser yamlParser = Mock(SnakeYamlParser)
        def configLoader = new ConfigLoader(yamlParser)

        when:
        yamlParser.parse("path") >> map
        configLoader.loadConfigs("path")

        then:
        configLoader.getConfig("firstKey", "secondKey") == "value"
    }

    def "getConfigThrowsIllegalStateExceptionWhenConfigsNotLoaded"() {
        given:
        def configLoader = new ConfigLoader(Mock(YamlParser))

        when:
        configLoader.getConfig("firstKey", "secondKey")

        then:
        thrown(IllegalStateException)
    }

    def "getConfigThrowsIllegalArgumentExceptionWhenConfigNotFound"() {
        given:
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>()
        Map<String, String> innerMap = new HashMap<String, String>()
        innerMap.put("secondKey", "value")
        map.put("firstKey", innerMap)

        YamlParser yamlParser = Mock(SnakeYamlParser)
        ConfigLoader configLoader = new ConfigLoader(yamlParser)

        when:
        yamlParser.parse("path") >> map
        configLoader.loadConfigs("path")
        configLoader.getConfig("firstKey", "thirdKey")

        then:
        thrown(IllegalArgumentException)
    }
}
