package main.ui.entries.candidate

import main.models.dtos.request.AddressDTO
import main.models.dtos.request.CandidateDTO
import main.models.dtos.request.SkillDTO
import main.services.interfaces.AddressService
import main.services.interfaces.CandidateService
import main.services.interfaces.SkillService
import main.ui.helpers.UserInfoCollector
import main.ui.interfaces.MenuCommand
import main.ui.interfaces.MenuState
import main.util.exception.ParamValidation

class RegisterCandidateEntry implements MenuCommand {

    private final CandidateService candidateService
    private final SkillService skillService
    private final AddressService addressService

    RegisterCandidateEntry(
            CandidateService candidateService,
            SkillService skillService,
            AddressService addressService
    ) {
        ParamValidation.requireNonNull(candidateService, "CandidateService cannot be null")
        ParamValidation.requireNonNull(skillService, "SkillService cannot be null")
        ParamValidation.requireNonNull(addressService, "AddressService cannot be null")

        this.candidateService = candidateService
        this.skillService = skillService
        this.addressService = addressService
    }

    @Override
    void execute() {
        registerProfile()
    }

    @Override
    String getEntryName() {
        return "Cadastrar perfil"
    }

    @Override
    MenuState getNextMenuState(MenuState currentState) {
        return currentState
    }

    private registerProfile() {
        Scanner scanner = new Scanner(System.in)

        Map<String, String> candidateInfo = UserInfoCollector.gatherCandidateInfo(scanner)
        Map<String, String> addressInfo = UserInfoCollector.gatherAddressInfo(scanner)
        Set<String> skillSet = UserInfoCollector.gatherSkills(scanner)

        int addressId = registerAddress(addressInfo)
        int candidateId = registerCandidate(addressId, candidateInfo)
        registerSkills(candidateId, skillSet)

        println "Candidato registrado com sucesso!"
    }

    private int registerAddress(Map<String, String> addressInfo) {
        AddressDTO addressDTO = AddressDTO.of(addressInfo)

        return addressService.save(addressDTO)
    }

    private int registerCandidate(int addressId, Map<String, String> candidateInfo) {
        candidateInfo.put("addressId", addressId.toString())
        CandidateDTO candidateDTO = CandidateDTO.of(candidateInfo)

        return candidateService.save(candidateDTO)
    }

    private void registerSkills(int candidateId, Set<String> skillSet) {
        Set<SkillDTO> skillDTOSet = skillSet.collect {
            new SkillDTO.Builder().name(it).build()
        }

        Set<Integer> ids = skillService.saveAll(skillDTOSet)

        for (int id : ids) {
            skillService.saveCandidateSkill(candidateId, id)
        }
    }
}
