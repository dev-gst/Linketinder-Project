package main.ui

import main.models.dtos.request.AddressDTO
import main.models.dtos.request.CandidateDTO
import main.models.dtos.request.SkillDTO
import main.models.dtos.request.login.LoginDetailsDTO
import main.models.entities.Candidate
import main.models.entities.JobOpening
import main.services.interfaces.AddressService
import main.services.interfaces.CandidateService
import main.services.interfaces.JobOpeningService
import main.services.interfaces.SkillService
import main.ui.util.Helpers

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class CandidateMenu {
    private static final int MENU_ENTRIES = 4

    private final CandidateService candidateService
    private final JobOpeningService jobOpeningService
    private final AddressService addressService
    private final SkillService skillService
    private Candidate candidate

    CandidateMenu(
            CandidateService candidateService,
            JobOpeningService jobOpeningService,
            AddressService addressService,
            SkillService skillService,
            Candidate candidate
    ) {
        this.candidateService = candidateService
        this.jobOpeningService = jobOpeningService
        this.addressService = addressService
        this.skillService = skillService
        this.candidate = candidate
    }

    void start() {
        do  {
            printMenu()
        } while (!selectOption(Helpers.getUsrChoice(MENU_ENTRIES)))
    }

    private boolean selectOption(int choice) {
        switch (choice) {
            case 1:
                viewJobOpenings()
                break
            case 2:
                updateCandidateInfo()
                break
            case 3:
                return deleteCandidateAccount()
            case 4:
                return true
            default:
                println "Opção inválida!"
        }

        return false
    }

    private void viewJobOpenings() {
        List<JobOpening> jobOpenings = jobOpeningService.getAll()
        if (jobOpenings.isEmpty()) {
            println "Nenhuma vaga disponível no momento."
        } else {
            println "***** Vagas Disponíveis *****"
            jobOpenings.each { jobOpening ->
                println "Nome: ${jobOpening.name}"
                println "Descrição: ${jobOpening.description}"
                println "Remoto: ${jobOpening.isRemote}"
                println "Aberta: ${jobOpening.isOpen}"
                println()
            }
        }
    }

    private void updateCandidateInfo() {
        Scanner scanner = new Scanner(System.in)

        println "***** Atualizar Informações do Candidato *****"

        print "Atualize o primeiro nome (${candidate.firstName}): "
        String firstName = Helpers.getStringFieldFromUsr(scanner)

        print "Atualize o sobrenome (${candidate.lastName}): "
        String lastName = Helpers.getStringFieldFromUsr(scanner)

        print "Atualize o email (${candidate.loginDetails.email}): "
        String email = Helpers.getStringFieldFromUsr(scanner)

        println "Atualize a senha: "
        String password = Helpers.getStringFieldFromUsr(scanner)

        print "Atualize a descrição (${candidate.description}): "
        String description = Helpers.getStringFieldFromUsr(scanner)

        print "Atualize a data de nascimento (${candidate.birthDate}): "
        Instant birthDate = Helpers.getInstantFieldFromUsr(scanner)

        print "Atualize o CPF (${candidate.cpf}): "
        String cpf = Helpers.getStringFieldFromUsr(scanner)

        print "Atualize a formação acadêmica (${candidate.education}): "
        String education = Helpers.getStringFieldFromUsr(scanner)

        AddressDTO addressDTO = Helpers.createAddress()
        Set<SkillDTO> skillDTOSet = Helpers.gatherSkills()

        int addressId = addressService.save(addressDTO)

        CandidateDTO candidateDTO = new CandidateDTO.Builder()
                .firstName(firstName)
                .lastName(lastName)
                .loginDetailsDTO(new LoginDetailsDTO(email, password))
                .description(description)
                .birthDate(LocalDate.ofInstant(birthDate, ZoneId.systemDefault()))
                .cpf(cpf)
                .education(education)
                .addressId(addressId)
                .build()

        Set<Integer> skills = skillService.saveAll(skillDTOSet)
        for (Integer skillId : skills) {
            skillService.saveCandidateSkill(candidate.id, skillId)
        }

        candidate = candidateService.updateById(candidate.id, candidateDTO)

        println "Informações atualizadas com sucesso!"
    }

    private boolean deleteCandidateAccount() {
        Scanner scanner = new Scanner(System.in)

        println "Tem certeza que deseja deletar sua conta? (S/N)"
        String confirmation = Helpers.getStringFieldFromUsr(scanner).trim().toLowerCase()

        if (confirmation == 's') {
            candidateService.deleteById(candidate.id)
            println "Conta deletada com sucesso."

            return true
        }

        println "Ação cancelada."

        return false
    }

    private static void printMenu() {
        println()
        println "***********************"
        println "1 - Ver Vagas"
        println "2 - Atualizar Informações"
        println "3 - Deletar Conta"
        println "4 - Voltar"
        println "***********************"
    }
}