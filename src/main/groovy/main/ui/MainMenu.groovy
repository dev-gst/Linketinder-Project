package main.ui

import main.services.CandidateService
import main.services.CompanyService
import main.services.JobOpeningService
import main.services.SkillService
import main.ui.util.Helpers

class MainMenu {
    static final int MENU_ENTRIES = 3

    final CandidateService candidateService
    final CompanyService companyService
    final JobOpeningService jobOpeningService
    final SkillService skillService

    MainMenu(
            CompanyService companyService,
            CandidateService candidateService,
            JobOpeningService jobOpeningService,
            SkillService skillService
    ) {
        this.candidateService = candidateService
        this.companyService = companyService
        this.jobOpeningService = jobOpeningService
        this.skillService = skillService
    }

    void start() {
        do {
            printMenu()
        } while (!selectOption(Helpers.getUsrChoice(MENU_ENTRIES)))
    }

    private boolean selectOption(int choice) {
        switch (choice) {
            case 1:
                CandidateAuthMenu candidateAuthMenu =
                        new CandidateAuthMenu(candidateService, jobOpeningService)

                candidateAuthMenu.start()
                break
            case 2:
                CompanyAuthMenu companyAuthMenu = new CompanyAuthMenu(companyService, jobOpeningService, candidateService)
                companyAuthMenu.start()
                break
            case MENU_ENTRIES:
                return true
            default:
                println "Opção inválida!"
        }

        return false
    }

    private static void printMenu() {
        println "***********************"
        println "1 - Sou um Candidato"
        println "2 - Sou uma Empresa"
        println "3 - Sair"
        println "***********************"
    }
}
