package main.database

import main.util.config.ConfigLoader
import main.util.config.Env
import main.util.parser.yaml.SnakeYamlParser
import main.util.parser.yaml.YamlParser
import org.flywaydb.core.Flyway
import org.yaml.snakeyaml.Yaml

class FlywayMigration {

    static void main(String[] args) {

        YamlParser yamlParser = new SnakeYamlParser(new Yaml())
        ConfigLoader configLoader = new ConfigLoader(yamlParser)
        Env env = new Env(configLoader)

        Flyway flyway = Flyway.configure()
                .dataSource(
                        env.getDbUrl(),
                        env.getDbUser(),
                        env.getDbPassword()
                )
                .locations(env.getFlywayLocation())
                .schemas(env.getDbSchemas())
                .load()
        flyway.migrate()
    }
}
