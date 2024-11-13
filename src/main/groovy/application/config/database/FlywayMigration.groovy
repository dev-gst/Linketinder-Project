package application.config.database

import application.config.ConfigLoader
import application.config.Env
import application.utils.parsers.yaml.SnakeYamlParser
import application.utils.parsers.yaml.YamlParser
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
