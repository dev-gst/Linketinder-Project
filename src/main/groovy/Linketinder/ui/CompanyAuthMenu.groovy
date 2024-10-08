package Linketinder.ui

import Linketinder.models.DTOs.AddressDTO
import Linketinder.models.DTOs.CompanyDTO
import Linketinder.models.entities.Company
import Linketinder.services.CandidateService
import Linketinder.services.CompanyService
import Linketinder.services.JobOpeningService
import Linketinder.ui.util.Helpers

import java.time.Instant

class CompanyAuthMenu {
    private static final int MENU_ENTRIES = 3

    private final CompanyService companyService
    private final CandidateService candidateService
    private final JobOpeningService jobOpeningService

    CompanyAuthMenu(
            CompanyService companyService,
            JobOpeningService jobOpeningService,
            CandidateService candidateService
    ) {
        this.companyService = companyService
        this.jobOpeningService = jobOpeningService
        this.candidateService = candidateService
    }

    void start() {
        do {
            printAuthMenu()
        } while (!selectAuthOption(Helpers.getUsrChoice(MENU_ENTRIES)))
    }

    private boolean selectAuthOption(int choice) {
        switch (choice) {
            case 1:
                registerCompany()
                return false
            case 2:
                loginCompany()
                return false
            case 3:
                return true
            default:
                println "Escolha inválida!"
        }
    }

    private void registerCompany() {
        Scanner scanner = new Scanner(System.in)
        CompanyDTO companyDTO = new CompanyDTO()

        print "Insira o nome da empresa: "
        String name = Helpers.getStringFieldFromUsr(scanner)

        print "Insira o email: "
        String email = Helpers.getStringFieldFromUsr(scanner)

        print "Insira a senha: "
        String password = Helpers.getStringFieldFromUsr(scanner)

        print "Insira a descrição: "
        String description = Helpers.getStringFieldFromUsr(scanner)

        print "Insira o CNPJ: "
        Instant cnpj = Helpers.getInstantFieldFromUsr(scanner)

        AddressDTO addressDTO = Helpers.createAddress()

        companyDTO.name = name
        companyDTO.email = email
        companyDTO.password = password
        companyDTO.description = description
        companyDTO.cnpj = cnpj

        companyService.save(companyDTO, addressDTO)

        println "Empresa registrada com sucesso!"
    }

    private void loginCompany() {
        Scanner scanner = new Scanner(System.in)

        println "***** Login de Empresa *****"
        println "Digite o email: "
        String email = Helpers.getStringFieldFromUsr(scanner)

        println "Digite a senha: "
        String password = Helpers.getStringFieldFromUsr(scanner)

        Company company = companyService.authenticate(email, password)

        if (company != null) {
            println "Login bem-sucedido! Bem-vindo, ${company.name}!"

            CompanyMenu companyMenu = new CompanyMenu(
                    companyService,
                    jobOpeningService,
                    candidateService,
                    company
            )

            companyMenu.start()

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
