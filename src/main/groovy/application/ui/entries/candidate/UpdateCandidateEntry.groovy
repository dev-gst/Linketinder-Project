package application.ui.entries.candidate

import application.models.dtos.request.AddressDTO
import application.models.dtos.request.CandidateDTO
import application.models.dtos.request.SkillDTO
import application.models.entities.Candidate
import application.services.interfaces.AddressService
import application.services.interfaces.CandidateService
import application.services.interfaces.SkillService
import application.ui.helpers.UserInfoCollector
import application.ui.interfaces.MenuCommand
import application.ui.interfaces.MenuState
import application.utils.validation.ParamValidation

class UpdateCandidateEntry implements MenuCommand {

    CandidateService candidateService
    AddressService addressService
    SkillService skillService

    Candidate candidate

    UpdateCandidateEntry(
            Candidate candidate,
            CandidateService candidateService,
            AddressService addressService,
            SkillService skillService
    ) {
        ParamValidation.requireNonNull(candidate, "Candidate cannot be null")
        ParamValidation.requireNonNull(candidateService, "CandidateService cannot be null")
        ParamValidation.requireNonNull(addressService, "AddressService cannot be null")
        ParamValidation.requireNonNull(skillService, "SkillService cannot be null")

        this.candidate = candidate
        this.candidateService = candidateService
        this.addressService = addressService
        this.skillService = skillService
    }

    @Override
    void execute() {
        updateProfile()
    }

    @Override
    String getEntryName() {
        return "Atualizar perfil"
    }

    @Override
    MenuState getNextMenuState(MenuState currentState) {
        return currentState
    }

    private void updateProfile() {
        Scanner scanner = new Scanner(System.in)

        Map<String, String> candidateInfo = UserInfoCollector.updateCandidateInfo(candidate, scanner)
        Map<String, String> addressInfo = UserInfoCollector.updateAddressInfo(scanner)
        Set<String> skills = UserInfoCollector.updateSkills(scanner)

        boolean firstUpdate = updateCandidateAddress(addressInfo)
        boolean secondUpdate = updateCandidateSkills(skills)
        boolean thirdUpdate = updateCandidate(candidateInfo)

        printCustomMessage(firstUpdate || secondUpdate || thirdUpdate)
    }

    private boolean updateCandidate(Map<String, String> candidateInfo) {
        if (candidateInfo == null) {
            return false
        }

        candidateInfo.put("addressId", candidate.addressId.toString())
        CandidateDTO candidateDTO = CandidateDTO.of(candidateInfo)

        candidate = candidateService.updateById(candidate.id, candidateDTO)

        return true
    }

    private boolean updateCandidateAddress(Map<String, String> addressInfo) {
        if (addressInfo == null) {
            return false
        }

        AddressDTO addressDTO = AddressDTO.of(addressInfo)

        addressService.updateById(candidate.addressId, addressDTO)

        return true
    }

    private boolean updateCandidateSkills(Set<String> skills) {
        if (skills == null) {
            return false
        }

        skillService.deleteAllCandidateSkills(candidate.id)

        Set<SkillDTO> skillDTOSet = skills.stream()
                .map(a -> SkillDTO.of(a))
                .collect() as LinkedHashSet

        Set<Integer> ids = skillService.saveAll(skillDTOSet)

        for (Integer id : ids) {
            skillService.saveCandidateSkill(candidate.id, id)
        }

        return true
    }

    private static void printCustomMessage(boolean condition) {
        println condition ? "Informações atualizadas com sucesso!" : "Nenhuma informação foi atualizada"
    }
}
