package Linketinder.ui

import Linketinder.models.DTOs.AddressDTO
import Linketinder.models.DTOs.CandidateDTO
import Linketinder.models.DTOs.SkillDTO
import Linketinder.models.entities.Candidate
import Linketinder.models.entities.JobOpening
import Linketinder.services.CandidateService
import Linketinder.services.JobOpeningService
import Linketinder.ui.util.Helpers

import java.time.Instant

class CandidateMenu {
    private static final int MENU_ENTRIES = 4

    private final CandidateService candidateService
    private final JobOpeningService jobOpeningService
    private Candidate candidate

    CandidateMenu(
            CandidateService candidateService,
            JobOpeningService jobOpeningService,
            Candidate candidate
    ) {
        this.candidateService = candidateService
        this.jobOpeningService = jobOpeningService
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
                println "Endereço: ${jobOpening.address ?: 'Não se aplica'}"
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

        print "Atualize o email (${candidate.email}): "
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

        CandidateDTO candidateDTO = new CandidateDTO()
        candidateDTO.firstName = firstName
        candidateDTO.lastName = lastName
        candidateDTO.email = email
        candidateDTO.password = password
        candidateDTO.description = description
        candidateDTO.birthDate = birthDate
        candidateDTO.cpf = cpf
        candidateDTO.education = education

        candidateService.update(candidate.id, candidateDTO, addressDTO, skillDTOSet)
        println "Informações atualizadas com sucesso!"
    }

    private boolean deleteCandidateAccount() {
        Scanner scanner = new Scanner(System.in)

        println "Tem certeza que deseja deletar sua conta? (S/N)"
        String confirmation = Helpers.getStringFieldFromUsr(scanner).trim().toLowerCase()

        if (confirmation == 's') {
            candidateService.delete(candidate.id)
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