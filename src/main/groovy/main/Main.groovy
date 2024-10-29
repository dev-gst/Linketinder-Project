package main

import main.database.DBConnectionManager
import main.repositories.*
import main.repositories.interfaces.*
import main.services.*
import main.services.interfaces.*
import main.ui.MainMenu
import main.util.config.ConfigLoader
import main.util.config.Env
import main.util.parser.yaml.SnakeYamlParser
import main.util.parser.yaml.YamlParser
import org.yaml.snakeyaml.Yaml

import java.sql.Connection

class Main {
    static void main(String[] args) {
        YamlParser yamlParser = new SnakeYamlParser(new Yaml())
        ConfigLoader configLoader = new ConfigLoader(yamlParser)
        Env env = new Env(configLoader)

        DBConnectionManager dbConnectionManager = new DBConnectionManager(env)
        Connection connection = dbConnectionManager.getConnection()

        AddressDAO addressDAO = new DefaultAddressDAO(connection)
        SkillDAO skillDAO = new DefaultSkillDAO(connection)
        CandidateDAO candidateDAO = new DefaultCandidateDAO(connection)
        CompanyDAO companyDAO = new DefaultCompanyDAO(connection)
        JobOpeningDAO jobOpeningDAO = new DefaultJobOpeningDAO(connection)

        AddressService addressService = new DefaultAddressService(addressDAO)
        SkillService skillService = new DefaultSkillService(skillDAO)
        CandidateService candidateService = new DefaultCandidateService(candidateDAO)
        CompanyService companyService = new DefaultCompanyService(companyDAO)
        JobOpeningService jobOpeningService = new DefaultJobOpeningService(jobOpeningDAO)

        MainMenu mainMenu = new MainMenu(
                companyService,
                candidateService,
                jobOpeningService,
                addressService,
                skillService
        )

        mainMenu.start()
    }
}

