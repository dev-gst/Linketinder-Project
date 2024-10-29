package main.ui

import main.models.dtos.request.AddressDTO
import main.models.dtos.request.CompanyDTO
import main.models.dtos.request.JobOpeningDTO
import main.models.dtos.request.SkillDTO
import main.models.dtos.request.login.LoginDetailsDTO
import main.models.entities.Candidate
import main.models.entities.Company
import main.models.entities.JobOpening
import main.models.entities.Skill
import main.services.interfaces.*
import main.ui.util.Helpers

class CompanyMenu {
    private static final int MENU_ENTRIES = 8

    private final CompanyService companyService
    private final JobOpeningService jobOpeningService
    private final CandidateService candidateService
    private final AddressService addressService
    private final SkillService skillService

    private Company company

    CompanyMenu(
            CompanyService companyService,
            JobOpeningService jobOpeningService,
            CandidateService candidateService,
            AddressService addressService,
            SkillService skillService,
            Company company
    ) {
        this.companyService = companyService
        this.jobOpeningService = jobOpeningService
        this.candidateService = candidateService
        this.addressService = addressService
        this.skillService = skillService
        this.company = company
    }

    void start() {
        do {
            printMenu()
        } while (!selectOption(Helpers.getUsrChoice(MENU_ENTRIES)))
    }

    private boolean selectOption(int choice) {
        switch (choice) {
            case 1:
                viewProfile()
                break
            case 2:
                updateProfile()
                break
            case 3:
                createJobOpening()
                break
            case 4:
                printJobOpenings()
                break
            case 5:
                updateJobOpening()
                break
            case 6:
                printCandidates()
                break
            case 7:
                deleteProfile()
                return true
            case MENU_ENTRIES:
                return true
            default:
                println "Escolha inválida!"
        }

        return false
    }

    private void viewProfile() {
        println "***** Perfil da Empresa *****"
        println "Nome: ${company.name}"
        println "Email: ${company.email}"
        println "Descrição: ${company.description}"
        println "CNPJ: ${company.cnpj}"
    }

    private void updateProfile() {
        Scanner scanner = new Scanner(System.in)

        print "Insira o novo nome da empresa: "
        String name = Helpers.getStringFieldFromUsr(scanner)

        print "Insira o novo email: "
        String email = Helpers.getStringFieldFromUsr(scanner)

        print "Insira a nova senha: "
        String password = Helpers.getStringFieldFromUsr(scanner)

        print "Insira a nova descrição: "
        String description = Helpers.getStringFieldFromUsr(scanner)

        print "Insira o novo CNPJ: "
        String cnpj = Helpers.getStringFieldFromUsr(scanner)

        AddressDTO addressDTO = Helpers.createAddress()
        int addressId = addressService.save(addressDTO)

        CompanyDTO companyDTO = new CompanyDTO.Builder()
                .setName(name)
                .setLoginDetailsDTO(new LoginDetailsDTO(email, password))
                .setDescription(description)
                .setCnpj(cnpj)
                .setAddressId(addressId)
                .build()

        company = companyService.updateById(company.id, companyDTO)

        println "Perfil atualizado com sucesso!"
    }

    private void createJobOpening() {
        Scanner scanner = new Scanner(System.in)

        print "Insira o nome da vaga: "
        String name = Helpers.getStringFieldFromUsr(scanner)

        print "Insira a descrição da vaga: "
        String description = Helpers.getStringFieldFromUsr(scanner)

        print "A vaga é remota? (s/N): "
        boolean isRemote = Helpers.getBooleanFromUsr(scanner)
        println "Sua resposta foi ${isRemote ? 'Sim' : 'Não'}"

        Set<SkillDTO> skillDTOSet = Helpers.gatherSkills()

        JobOpeningDTO jobOpeningDTO
        if (!isRemote) {
            AddressDTO addressDTO = Helpers.createAddress()
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

    private void updateJobOpening() {
        Scanner scanner = new Scanner(System.in)

        print "Insira o ID da vaga a ser atualizada: "
        int jobOpeningId = Helpers.getIntFromUsr(scanner)

        JobOpening existingJobOpening = jobOpeningService.getById(jobOpeningId)
        Company existingCompany = companyService.getByEntityId(existingJobOpening.id, JobOpening.class)[0]
        if (existingJobOpening == null || existingCompany.id != company.id) {
            println "Vaga não encontrada ou não pertence à empresa."
            return
        }

        println()
        print "Insira o novo nome da vaga (atual: ${existingJobOpening.name}): "
        String name = Helpers.getStringFieldFromUsr(scanner)

        print "Insira a nova descrição da vaga (atual: ${existingJobOpening.description}): "
        String description = Helpers.getStringFieldFromUsr(scanner)

        print "A vaga é remota? (atual: ${existingJobOpening.isRemote ? 'Sim' : 'Não'}) (s/n): "
        boolean isRemote = Helpers.getBooleanFromUsr(scanner)
        Set<SkillDTO> skillDTOSet = Helpers.gatherSkills()

        JobOpeningDTO jobOpeningDTO
        if (!isRemote) {
            AddressDTO addressDTO = Helpers.createAddress()
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

        jobOpeningService.updateById(jobOpeningId, jobOpeningDTO).id
        Set<Integer> skillIds = skillService.saveAll(skillDTOSet)

        for (int skillId : skillIds) {
            skillService.saveJobOpeningSkill(jobOpeningId, skillId)
        }

        println "Vaga atualizada com sucesso!"
    }

    private void printJobOpenings() {
        List<JobOpening> jobOpenings = jobOpeningService.getByEntityId(company.id, Company.class)
        println "***** Vagas da Empresa *****"
        jobOpenings.each { jobOpening ->
            println "ID: ${jobOpening.id}"
            println "Nome: ${jobOpening.name}"
            println "Descrição: ${jobOpening.description}"
            println "Remota: ${jobOpening.isRemote ? 'Sim' : 'Não'}"
            println "Aberta: ${jobOpening.isOpen ? 'Sim' : 'Não'}"
            println "-----------------------------"
        }
    }

    private void printCandidates() {
        Set<Candidate> candidates = candidateService.getAll()

        println "***** Candidatos *****"
        candidates.each { candidate ->
            Set<Skill> candidateSkills = skillService.getByEntityId(candidate.id, Candidate.class)
            println "Descrição: ${candidate.description}"
            println "Skills: ${candidateSkills}"
            println "Formação: ${candidate.education}"
            println "-----------------------------"
        }
    }

    private void deleteProfile() {
        Scanner scanner = new Scanner(System.in)
        println "Tem certeza que deseja deletar o perfil? (s/N)"
        boolean confirm = Helpers.getBooleanFromUsr(scanner)

        if (!confirm) {
            println "Operação cancelada."
            return
        }

        companyService.deleteById(company.id)
        println "Perfil deletado com sucesso!"
    }

    private static void printMenu() {
        println()
        println "***********************"
        println "1 - Ver Perfil"
        println "2 - Atualizar Perfil"
        println "3 - Criar Vaga"
        println "4 - Ver Vagas"
        println "5 - Atualizar Vaga"
        println "6 - Ver Candidatos"
        println "7 - Delete Profile"
        println "8 - Voltar"
        println "***********************"
    }
}
