package main.ui

import main.services.interfaces.*
import main.ui.util.Helpers

class MainMenu {
    static final int MENU_ENTRIES = 3

    final CandidateService candidateService
    final CompanyService companyService
    final JobOpeningService jobOpeningService
    final AddressService addressService
    final SkillService skillService

    MainMenu(
            CompanyService companyService,
            CandidateService candidateService,
            JobOpeningService jobOpeningService,
            AddressService addressService,
            SkillService skillService
    ) {
        this.candidateService = candidateService
        this.companyService = companyService
        this.jobOpeningService = jobOpeningService
        this.addressService = addressService
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
                        new CandidateAuthMenu(candidateService, jobOpeningService, addressService, skillService)

                candidateAuthMenu.start()
                break
            case 2:
                CompanyAuthMenu companyAuthMenu = new CompanyAuthMenu(
                        companyService,
                        jobOpeningService,
                        candidateService,
                        addressService,
                        skillService
                )
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
