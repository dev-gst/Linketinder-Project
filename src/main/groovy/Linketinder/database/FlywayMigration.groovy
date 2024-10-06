package Linketinder.database

import Linketinder.config.Env
import org.flywaydb.core.Flyway

class FlywayMigration {

    static void main(String[] args) {

        Flyway flyway = Flyway.configure()
                .dataSource(
                        Env.DB_URL,
                        Env.DB_USER,
                        Env.DB_PASSWORD
                )
                .locations(Env.DB_LOCATIONS)
                .schemas(Env.DB_SCHEMAS)
                .load()
        flyway.migrate()
    }
}
