package main.ui

import main.models.dtos.request.AddressDTO
import main.models.dtos.request.CompanyDTO
import main.models.dtos.request.login.LoginDetailsDTO
import main.models.entities.Company
import main.services.interfaces.*
import main.ui.util.Helpers

class CompanyAuthMenu {
    private static final int MENU_ENTRIES = 3

    private final CompanyService companyService
    private final CandidateService candidateService
    private final JobOpeningService jobOpeningService
    private final AddressService addressService
    private final SkillService skillService

    CompanyAuthMenu(
            CompanyService companyService,
            JobOpeningService jobOpeningService,
            CandidateService candidateService,
            AddressService addressService,
            SkillService skillService
    ) {
        this.companyService = companyService
        this.jobOpeningService = jobOpeningService
        this.candidateService = candidateService
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

        print "Insira o nome da empresa: "
        String name = Helpers.getStringFieldFromUsr(scanner)

        print "Insira o email: "
        String email = Helpers.getStringFieldFromUsr(scanner)

        print "Insira a senha: "
        String password = Helpers.getStringFieldFromUsr(scanner)

        print "Insira a descrição: "
        String description = Helpers.getStringFieldFromUsr(scanner)

        print "Insira o CNPJ: "
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

        companyService.save(companyDTO)

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
                    addressService,
                    skillService,
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
