package main.ui

import main.models.DTOs.AddressDTO
import main.models.DTOs.CompanyDTO
import main.models.DTOs.JobOpeningDTO
import main.models.DTOs.SkillDTO
import main.models.entities.Candidate
import main.models.entities.Company
import main.models.entities.JobOpening
import main.services.CandidateService
import main.services.CompanyService
import main.services.JobOpeningService
import main.ui.util.Helpers

class CompanyMenu {
    private static final int MENU_ENTRIES = 8

    private final CompanyService companyService
    private final JobOpeningService jobOpeningService
    private final CandidateService candidateService
    private Company company

    CompanyMenu(
            CompanyService companyService,
            JobOpeningService jobOpeningService,
            CandidateService candidateService,
            Company company
    ) {
        this.companyService = companyService
        this.jobOpeningService = jobOpeningService
        this.candidateService = candidateService
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
        CompanyDTO companyDTO = new CompanyDTO()

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

        companyDTO.name = name
        companyDTO.email = email
        companyDTO.password = password
        companyDTO.description = description
        companyDTO.cnpj = cnpj

        companyService.update(company.id, companyDTO, addressDTO)
        company = companyService.getById(company.id)

        println "Perfil atualizado com sucesso!"
    }

    private void createJobOpening() {
        Scanner scanner = new Scanner(System.in)
        JobOpeningDTO jobOpeningDTO = new JobOpeningDTO()

        print "Insira o nome da vaga: "
        String name = Helpers.getStringFieldFromUsr(scanner)

        print "Insira a descrição da vaga: "
        String description = Helpers.getStringFieldFromUsr(scanner)

        print "A vaga é remota? (s/N): "
        boolean isRemote = Helpers.getBooleanFromUsr(scanner)
        println "Sua resposta foi ${isRemote ? 'Sim' : 'Não'}"

        AddressDTO addressDTO = null
        if (!isRemote) {
           addressDTO = Helpers.createAddress()
        }

        Set<SkillDTO> skillDTOSet = Helpers.gatherSkills()

        jobOpeningDTO.name = name
        jobOpeningDTO.description = description
        jobOpeningDTO.isOpen = true
        jobOpeningDTO.isRemote = company.id

        jobOpeningService.save(jobOpeningDTO, company.id, addressDTO, skillDTOSet)

        println "Vaga criada com sucesso!"
    }

    private void updateJobOpening() {
        Scanner scanner = new Scanner(System.in)

        print "Insira o ID da vaga a ser atualizada: "
        int jobOpeningId = Helpers.getIntFromUsr(scanner)

        JobOpening existingJobOpening = jobOpeningService.getById(jobOpeningId)
        if (existingJobOpening == null || existingJobOpening.company.id != company.id) {
            println "Vaga não encontrada ou não pertence à empresa."
            return
        }

        JobOpeningDTO jobOpeningDTO = new JobOpeningDTO()

        println()
        print "Insira o novo nome da vaga (atual: ${existingJobOpening.name}): "
        String name = Helpers.getStringFieldFromUsr(scanner)

        print "Insira a nova descrição da vaga (atual: ${existingJobOpening.description}): "
        String description = Helpers.getStringFieldFromUsr(scanner)

        print "A vaga é remota? (atual: ${existingJobOpening.isRemote ? 'Sim' : 'Não'}) (s/n): "
        boolean isRemote = Helpers.getBooleanFromUsr(scanner)

        AddressDTO addressDTO = null
        if (!isRemote) {
            addressDTO = Helpers.createAddress()
        }

        Set<SkillDTO> skillDTOSet = Helpers.gatherSkills()

        jobOpeningDTO.name = name
        jobOpeningDTO.description = description
        jobOpeningDTO.isOpen = existingJobOpening.isOpen
        jobOpeningDTO.isRemote = isRemote

        jobOpeningService.update(jobOpeningId, jobOpeningDTO, company.id, addressDTO, skillDTOSet)

        println "Vaga atualizada com sucesso!"
    }

    private void printJobOpenings() {
        List<JobOpening> jobOpenings = jobOpeningService.getByCompanyId(company.id)
        println "***** Vagas da Empresa *****"
        jobOpenings.each { jobOpening ->
            println "ID: ${jobOpening.id}"
            println "Nome: ${jobOpening.name}"
            println "Descrição: ${jobOpening.description}"
            println "Remota: ${jobOpening.isRemote ? 'Sim' : 'Não'}"
            println "Aberta: ${jobOpening.isOpen ? 'Sim' : 'Não'}"
            println "Endereço: ${jobOpening.address ?: 'Não se aplica'}"
            println "-----------------------------"
        }
    }

    private void printCandidates() {
        List<Candidate> candidates = candidateService.getAll()
        println "***** Candidatos *****"
        candidates.each { candidate ->
            println "Descrição: ${candidate.description}"
            println "Skills: ${candidate.skills.collect { it.name }}"
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

        companyService.delete(company.id)
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
