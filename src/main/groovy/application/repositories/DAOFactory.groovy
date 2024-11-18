package application.repositories

import application.config.ConfigLoader
import application.config.Env
import application.config.database.DBConnectionManager
import application.repositories.interfaces.*
import application.utils.parsers.yaml.SnakeYamlParser
import application.utils.parsers.yaml.YamlParser
import org.yaml.snakeyaml.Yaml

import java.sql.Connection

class DAOFactory {

    private static Connection connection

    private static AddressDAO addressDAO
    private static CandidateDAO candidateDAO
    private static CompanyDAO companyDAO
    private static JobOpeningDAO jobOpeningDAO
    private static SkillDAO skillDAO

    static {
        YamlParser yamlParser = new SnakeYamlParser(new Yaml())
        ConfigLoader configLoader = new ConfigLoader(yamlParser)
        Env env = new Env(configLoader)

        connection = DBConnectionManager.getInstance(env).getConnection()

        addressDAO = new DefaultAddressDAO(connection)
        candidateDAO = new DefaultCandidateDAO(connection)
        companyDAO = new DefaultCompanyDAO(connection)
        jobOpeningDAO = new DefaultJobOpeningDAO(connection)
        skillDAO = new DefaultSkillDAO(connection)
    }

    static CandidateDAO createCandidateDAO() {
        return candidateDAO
    }

    static AddressDAO createAddressDAO() {
        return addressDAO
    }

    static CompanyDAO createCompanyDAO() {
        return companyDAO
    }

    static JobOpeningDAO createJobOpeningDAO() {
        return jobOpeningDAO
    }

    static SkillDAO createSkillDAO() {
        return skillDAO
    }
}
