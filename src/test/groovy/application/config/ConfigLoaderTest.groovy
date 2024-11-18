package application.config

import application.utils.io.FileSearcher
import application.utils.parsers.yaml.SnakeYamlParser
import application.utils.parsers.yaml.YamlParser
import spock.lang.Specification

class ConfigLoaderTest extends Specification {

    def "constructor throws IllegalArgumentException when YamlParser is null"() {
        when:
        new ConfigLoader(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "loadConfigs works correctly"() {
        given:
        YamlParser yamlParser = Mock(SnakeYamlParser)
        def configLoader = new ConfigLoader(yamlParser)

        when:
        configLoader.loadConfigs("path")

        then:
        1 * yamlParser.parse("path")
    }

    def "loadConfigs searches for file path when file is not found"() {
        given:
        String oldPath = "/test/some/path/to/test.yaml"
        String newPath = "/test/correct/test.yaml"

        YamlParser yamlParser = Mock(SnakeYamlParser)
        ConfigLoader configLoader = new ConfigLoader(yamlParser)
        GroovyMock(FileSearcher, global: true)

        yamlParser.parse(oldPath) >> { throw new FileNotFoundException() }
        FileSearcher.search(oldPath) >> newPath
        yamlParser.parse(newPath) >> new HashMap<String, Map<String, String>>()

        when:
        configLoader.loadConfigs(oldPath)

        then:
        1 * yamlParser.parse(oldPath) >> { throw new FileNotFoundException() }
        1 * yamlParser.parse(newPath) >> new HashMap<String, Map<String, String>>()
    }

    def "getConfig returns correct value"() {
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

    def "getConfig throws IllegalStateException when configs are not loaded"() {
        given:
        def configLoader = new ConfigLoader(Mock(YamlParser))

        when:
        configLoader.getConfig("firstKey", "secondKey")

        then:
        thrown(IllegalStateException)
    }

    def "getConfig throws IllegalArgumentException when configs are not found"() {
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
