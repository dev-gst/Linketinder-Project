package main.ui

import main.models.DTOs.AddressDTO
import main.models.DTOs.CandidateDTO
import main.models.DTOs.SkillDTO
import main.models.entities.Candidate
import main.services.CandidateService
import main.services.JobOpeningService
import main.ui.util.Helpers

import java.time.Instant

class CandidateAuthMenu {
    private static final int MENU_ENTRIES = 3

    private final CandidateService candidateService
    private final JobOpeningService jobOpeningService

    CandidateAuthMenu(
            CandidateService candidateService,
            JobOpeningService jobOpeningService
    ) {
        this.candidateService = candidateService
        this.jobOpeningService = jobOpeningService
    }

    void start() {
        do {
            printAuthMenu()
        } while (!selectAuthOption(Helpers.getUsrChoice(MENU_ENTRIES)))
    }

    private boolean selectAuthOption(int choice) {
        switch (choice) {
            case 1:
                registerCandidate()
                return false
            case 2:
                loginCandidate()
                return false
            case 3:
                return true
            default:
                println "Escolha inválida!"
        }
    }

    private void registerCandidate() {
        Scanner scanner = new Scanner(System.in)
        CandidateDTO candidateDTO = new CandidateDTO()

        print "Insira seu primeiro nome: "
        String firstName = Helpers.getStringFieldFromUsr(scanner)

        print "Insira seu sobrenome: "
        String lastName = Helpers.getStringFieldFromUsr(scanner)

        print "Insira seu email: "
        String email = Helpers.getStringFieldFromUsr(scanner)

        print "Insira sua senha: "
        String password = Helpers.getStringFieldFromUsr(scanner)

        print "Insira sua BIO: "
        String description = Helpers.getStringFieldFromUsr(scanner)

        print "Insira sua data de nascimento(YYYY-MM-DD): "
        Instant birthDate = Helpers.getInstantFieldFromUsr(scanner)

        print "Insira seu CPF: "
        String cpf = Helpers.getStringFieldFromUsr(scanner)

        print "Insira sua formação acadêmica: "
        String education = Helpers.getStringFieldFromUsr(scanner)

        AddressDTO addressDTO = Helpers.createAddress()
        Set<SkillDTO> skillDTOList = Helpers.gatherSkills()

        candidateDTO.firstName = firstName
        candidateDTO.lastName = lastName
        candidateDTO.email = email
        candidateDTO.password = password
        candidateDTO.description = description
        candidateDTO.birthDate = birthDate
        candidateDTO.cpf = cpf
        candidateDTO.education = education

        candidateService.save(candidateDTO, addressDTO, skillDTOList)

        println "Candidato registrado com sucesso!"
    }

    private void loginCandidate() {
        Scanner scanner = new Scanner(System.in)

        println "***** Login de Candidato *****"
        println "Digite o email: "
        String email = Helpers.getStringFieldFromUsr(scanner)

        println "Digite a senha: "
        String password = Helpers.getStringFieldFromUsr(scanner)

        Candidate candidate = candidateService.authenticate(email, password)

        if (candidate != null) {
            println "Login bem-sucedido! Bem-vindo, ${candidate.firstName}!"

            CandidateMenu candidateMenu = new CandidateMenu(
                    candidateService,
                    jobOpeningService,
                    candidate
            )

            candidateMenu.start()

        } else {
            println "Falha no login. Verifique suas credenciais."
        }
    }

    private static void printAuthMenu() {
        println()
        println "***********************"
        println "1 - Registrar"
        println "2 - Login"
        println "3 - Voltar"
        println "***********************"
    }
}
