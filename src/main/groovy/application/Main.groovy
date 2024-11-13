package application

import application.config.ConfigLoader
import application.config.Env
import application.config.database.DBConnectionManager
import application.controllers.DefaultMenuController
import application.repositories.*
import application.repositories.interfaces.*
import application.services.*
import application.services.interfaces.*
import application.ui.MenuFactory
import application.ui.interfaces.MenuState
import application.utils.parsers.yaml.SnakeYamlParser
import application.utils.parsers.yaml.YamlParser
import org.yaml.snakeyaml.Yaml

import java.sql.Connection

class Main {

    static void main(String[] args) {
        YamlParser yamlParser = new SnakeYamlParser(new Yaml())
        ConfigLoader configLoader = new ConfigLoader(yamlParser)
        Env env = new Env(configLoader)

        DBConnectionManager dbConnectionManager = DBConnectionManager.getInstance(env)
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

        MenuFactory menuFactory = new MenuFactory(
                candidateService,
                companyService,
                jobOpeningService,
                addressService,
                skillService,
        )

        MenuState initialState = menuFactory.createSelectionMenu()

        DefaultMenuController menuController = new DefaultMenuController(initialState)
        menuController.run()
    }
}

