package main.ui.entries.jobopenings

import main.models.dtos.request.AddressDTO
import main.models.dtos.request.JobOpeningDTO
import main.models.dtos.request.SkillDTO
import main.models.entities.Company
import main.services.interfaces.AddressService
import main.services.interfaces.JobOpeningService
import main.services.interfaces.SkillService
import main.ui.helpers.UserInfoCollector
import main.ui.interfaces.MenuCommand
import main.ui.interfaces.MenuState
import main.util.exception.ParamValidation

import static main.ui.helpers.UserInputCollector.getBoolean
import static main.ui.helpers.UserInputCollector.getString

class CreateJobOpeningEntry implements MenuCommand {

    private final JobOpeningService jobOpeningService
    private final AddressService addressService
    private final SkillService skillService
    private final Company company

    CreateJobOpeningEntry(
            JobOpeningService jobOpeningService,
            AddressService addressService,
            SkillService skillService,
            Company company
    ) {
        ParamValidation.requireNonNull(jobOpeningService, "JobOpeningService cannot be null")
        ParamValidation.requireNonNull(addressService, "AddressService cannot be null")
        ParamValidation.requireNonNull(skillService, "SkillService cannot be null")
        ParamValidation.requireNonNull(company, "Company cannot be null")

        this.jobOpeningService = jobOpeningService
        this.addressService = addressService
        this.skillService = skillService
        this.company = company
    }

    @Override
    void execute() {
        createJobOpening()
    }

    @Override
    String getEntryName() {
        return "Criar Vaga"
    }

    @Override
    MenuState getNextMenuState(MenuState currentState) {
        return currentState
    }

    private void createJobOpening() {
        Scanner scanner = new Scanner(System.in)

        print "Insira o nome da vaga: "
        String name = getString(scanner)

        print "Insira a descrição da vaga: "
        String description = getString(scanner)

        print "A vaga é remota? (s/N): "
        boolean isRemote = getBoolean(scanner)
        println "Sua resposta foi ${isRemote ? 'Sim' : 'Não'}"

        Set<String> skillSet = UserInfoCollector.gatherSkills(scanner)
        Set<SkillDTO> skillDTOSet = skillSet.stream()
                .map(s -> SkillDTO.of(s))
                .collect()

        JobOpeningDTO jobOpeningDTO
        if (!isRemote) {
            Map<String, String> addressMap = UserInfoCollector.gatherAddressInfo(scanner)
            AddressDTO addressDTO = AddressDTO.of(addressMap)
            int addressId = addressService.save(addressDTO)

            jobOpeningDTO = new JobOpeningDTO.Builder()
                    .setName(name)
                    .setDescription(description)
                    .setIsOpen(true)
                    .setIsRemote(isRemote)
                    .setCompanyId(company.id)
                    .setAddressId(Optional.of(addressId))
                    .build()
        } else {
            jobOpeningDTO = new JobOpeningDTO.Builder()
                    .setName(name)
                    .setDescription(description)
                    .setIsOpen(true)
                    .setIsRemote(isRemote)
                    .setCompanyId(company.id)
                    .setAddressId(Optional.of(null))
                    .build()
        }

        int jobOpeningId = jobOpeningService.save(jobOpeningDTO)
        Set<Integer> skillIds = skillService.saveAll(skillDTOSet)

        for (int skillId : skillIds) {
            skillService.saveJobOpeningSkill(jobOpeningId, skillId)
        }

        println "Vaga criada com sucesso!"
    }
}
