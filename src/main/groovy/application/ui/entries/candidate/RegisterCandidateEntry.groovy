package application.ui.entries.candidate

import application.models.dtos.request.AddressDTO
import application.models.dtos.request.CandidateDTO
import application.models.dtos.request.SkillDTO
import application.services.interfaces.AddressService
import application.services.interfaces.CandidateService
import application.services.interfaces.SkillService
import application.ui.helpers.UserInfoCollector
import application.ui.interfaces.MenuCommand
import application.ui.interfaces.MenuState
import application.utils.validation.ParamValidation

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
