package application.config.database

import application.config.ConfigLoader
import application.config.Env
import application.utils.parsers.yaml.SnakeYamlParser
import application.utils.parsers.yaml.YamlParser
import org.flywaydb.core.Flyway
import org.yaml.snakeyaml.Yaml

import java.sql.Connection
import java.sql.PreparedStatement

class FlywayMigration {

    static private Env env
    static private Connection conn

    static {
        YamlParser yamlParser = new SnakeYamlParser(new Yaml())
        ConfigLoader configLoader = new ConfigLoader(yamlParser)

        env = new Env(configLoader)
        conn = DBConnectionFactory.createPostgresCustomConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                env.getDbUser(),
                env.getDbPassword(),
                env.getDbDriver()
        ).getConnection()
    }

    static void main(String[] args) {
        dropDB(env)
        createDB(env)

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

    static void dropDB(Env env) {
        String query = "DROP DATABASE IF EXISTS " + env.getDbName()
        PreparedStatement stmt = conn.prepareStatement(query)

        stmt.execute()
    }

    static void createDB(Env env) {
        String query = "CREATE DATABASE " + env.getDbName()
        PreparedStatement stmt = conn.prepareStatement(query)

        stmt.execute()
    }
}
