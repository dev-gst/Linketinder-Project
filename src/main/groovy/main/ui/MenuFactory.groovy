package main.ui

import main.models.entities.Candidate
import main.services.interfaces.*
import main.ui.entries.candidate.*
import main.ui.entries.common.BackEntry
import main.ui.entries.jobopenings.ViewJobOpenings
import main.ui.interfaces.MenuCommand
import main.ui.interfaces.MenuState

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

    MenuState createCandidateMenu(Candidate candidate) {
        Map<Integer, MenuCommand> candidateMenuCommands = new HashMap<>()
        candidateMenuCommands.put(1, new ViewCandidateEntry(candidate, skillService, addressService))
        candidateMenuCommands.put(2, new UpdateCandidateEntry(candidate, candidateService, addressService, skillService))
        candidateMenuCommands.put(3, new DeleteCandidateEntry(candidate, candidateService))
        candidateMenuCommands.put(4, new ViewJobOpenings(jobOpeningService, addressService))
        candidateMenuCommands.put(5, new BackEntry())

        return new BaseMenuState(candidateMenuCommands, "Olá, $candidate.firstName")
    }

    MenuState createCandidateAuthMenu() {
        Map<Integer, MenuCommand> candidateAuthMenuCommands = new HashMap<>()
        candidateAuthMenuCommands.put(1, new RegisterCandidateEntry(candidateService, skillService, addressService))
        candidateAuthMenuCommands.put(2, new LoginCandidateEntry(candidateService, this))
        candidateAuthMenuCommands.put(3, new BackEntry())

        return new BaseMenuState(candidateAuthMenuCommands, "Menu de Candidato")
    }
}
