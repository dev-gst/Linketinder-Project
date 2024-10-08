package Linketinder

import Linketinder.database.DBConnectionManager
import Linketinder.repositories.AddressDAO
import Linketinder.repositories.CandidateDAO
import Linketinder.repositories.CompanyDAO
import Linketinder.repositories.JobOpeningDAO
import Linketinder.repositories.SkillDAO
import Linketinder.services.CandidateService
import Linketinder.services.CompanyService
import Linketinder.services.JobOpeningService
import Linketinder.services.SkillService
import Linketinder.ui.MainMenu

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
        JobOpeningService jobOpeningService = new JobOpeningService(jobOpeningDAO, skillDAO, companyService, addressDAO)

        MainMenu mainMenu = new MainMenu(
                companyService,
                candidateService,
                jobOpeningService,
                skillService
        )

        mainMenu.start()
    }
}

