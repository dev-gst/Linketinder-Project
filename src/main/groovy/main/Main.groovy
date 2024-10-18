package main

import main.database.DBConnectionManager
import main.repositories.*
import main.services.CandidateService
import main.services.CompanyService
import main.services.JobOpeningService
import main.services.SkillService
import main.ui.MainMenu

import java.sql.Connection

class Main {
    static void main(String[] args) {
        DBConnectionManager dbConnectionManager = DBConnectionManager.getInstance()
        Connection connection = dbConnectionManager.getConnection()

        AddressDAO addressDAO = new AddressDAO(connection)
        SkillDAO skillDAO = new SkillDAO(connection)
        CandidateDAO candidateDAO = new CandidateDAO(connection)
        CompanyDAO companyDAO = new CompanyDAO(connection)
        JobOpeningDAO jobOpeningDAO = new JobOpeningDAO(connection)

        SkillService skillService = new SkillService(skillDAO)
        CandidateService candidateService = new CandidateService(candidateDAO, addressDAO, skillDAO, skillService)
        CompanyService companyService = new CompanyService(companyDAO, jobOpeningDAO, addressDAO, skillDAO)
        JobOpeningService jobOpeningService = new JobOpeningService(jobOpeningDAO, skillDAO, skillService, companyService, addressDAO)

        MainMenu mainMenu = new MainMenu(
                companyService,
                candidateService,
                jobOpeningService,
                skillService
        )

        mainMenu.start()
    }
}

