package main.ui

import main.models.dtos.request.AddressDTO
import main.models.dtos.request.CandidateDTO
import main.models.dtos.request.SkillDTO
import main.models.dtos.request.login.LoginDetailsDTO
import main.models.entities.Candidate
import main.services.interfaces.AddressService
import main.services.interfaces.CandidateService
import main.services.interfaces.JobOpeningService
import main.services.interfaces.SkillService
import main.ui.util.Helpers

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class CandidateAuthMenu {
    private static final int MENU_ENTRIES = 3

    private final CandidateService candidateService
    private final JobOpeningService jobOpeningService
    private final AddressService addressService
    private final SkillService skillService

    CandidateAuthMenu(
            CandidateService candidateService,
            JobOpeningService jobOpeningService,
            AddressService addressService,
            SkillService skillService
    ) {
        this.candidateService = candidateService
        this.jobOpeningService = jobOpeningService
        this.addressService = addressService
        this.skillService = skillService
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
        CandidateDTO candidateDTO = new CandidateDTO.Builder()
                .firstName(firstName)
                .lastName(lastName)
                .loginDetailsDTO(new LoginDetailsDTO(email, password))
                .description(description)
                .birthDate(LocalDate.ofInstant(birthDate, ZoneId.systemDefault()))
                .cpf(cpf)
                .education(education)
                .addressId(addressService.save(addressDTO))
                .build()

        int CandidateId = candidateService.save(candidateDTO)
        Set<Integer> skillIds = skillService.saveAll(skillDTOList)

        for (int skillId : skillIds) {
            skillService.saveCandidateSkill(CandidateId, skillId)
        }

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
                    addressService,
                    skillService,
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
