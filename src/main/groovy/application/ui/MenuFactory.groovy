package application.ui

import application.models.entities.Candidate
import application.models.entities.Company
import application.services.interfaces.*
import application.ui.entries.candidate.*
import application.ui.entries.common.BackEntry
import application.ui.entries.common.ExitEntry
import application.ui.entries.company.*
import application.ui.entries.jobopenings.CreateJobOpeningEntry
import application.ui.entries.jobopenings.ViewAllJobOpeningsEntry
import application.ui.entries.jobopenings.ViewCompanyJobOpeningsEntry
import application.ui.interfaces.MenuCommand
import application.ui.interfaces.MenuState

class MenuFactory {

    private final CandidateService candidateService
    private final CompanyService companyService
    private final JobOpeningService jobOpeningService
    private final AddressService addressService
    private final SkillService skillService

    MenuFactory(
            CandidateService candidateService,
            CompanyService companyService,
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

    MenuState createSelectionMenu() {
        Map<Integer, MenuCommand> selectionMenu = new HashMap<>()
        selectionMenu.put(1, new SelectCandidateEntry(this))
        selectionMenu.put(2, new SelectCompanyEntry(this))
        selectionMenu.put(3, new ExitEntry())

        return new BaseMenuState(selectionMenu, "Bem-vindo ao sistema de recrutamento")
    }

    MenuState createCandidateAuthMenu() {
        Map<Integer, MenuCommand> candidateAuthMenuCommands = new HashMap<>()
        candidateAuthMenuCommands.put(1, new RegisterCandidateEntry(candidateService, skillService, addressService))
        candidateAuthMenuCommands.put(2, new LoginCandidateEntry(candidateService, this))
        candidateAuthMenuCommands.put(3, new BackEntry())

        return new BaseMenuState(candidateAuthMenuCommands, "Menu do Candidato")
    }

    MenuState createCandidateMenu(Candidate candidate) {
        Map<Integer, MenuCommand> candidateMenuCommands = new HashMap<>()
        candidateMenuCommands.put(1, new ViewCandidateEntry(candidate, skillService, addressService))
        candidateMenuCommands.put(2, new UpdateCandidateEntry(candidate, candidateService, addressService, skillService))
        candidateMenuCommands.put(3, new DeleteCandidateEntry(candidate, candidateService))
        candidateMenuCommands.put(4, new ViewAllJobOpeningsEntry(jobOpeningService, addressService))
        candidateMenuCommands.put(5, new BackEntry())

        return new BaseMenuState(candidateMenuCommands, "Olá, $candidate.firstName")
    }

    MenuState createCompanyAuthMenu() {
        Map<Integer, MenuCommand> companyAuthMenuCommands = new HashMap<>()
        companyAuthMenuCommands.put(1, new RegisterCompanyEntry(companyService, addressService))
        companyAuthMenuCommands.put(2, new LoginCompanyEntry(companyService, this))
        companyAuthMenuCommands.put(3, new BackEntry())

        return new BaseMenuState(companyAuthMenuCommands, "Menu da Empresa")
    }

    MenuState createCompanyMenu(Company company) {
        Map<Integer, MenuCommand> companyMenuCommands = new HashMap<>()
        companyMenuCommands.put(1, new ViewCompanyEntry(company, addressService))
        companyMenuCommands.put(2, new ViewCompanyJobOpeningsEntry(jobOpeningService, company))
        companyMenuCommands.put(3, new CreateJobOpeningEntry(jobOpeningService, addressService, skillService, company))
        companyMenuCommands.put(4, new DeleteCompanyEntry(company, companyService))
        companyMenuCommands.put(5, new BackEntry())

        return new BaseMenuState(companyMenuCommands, "Menu da Empresa")
    }
}
