package main.ui.entries.company

import main.models.entities.Company
import main.services.interfaces.CompanyService
import main.ui.MenuFactory
import main.ui.interfaces.MenuCommand
import main.ui.interfaces.MenuState
import main.util.exception.ParamValidation

import static main.ui.helpers.UserInputCollector.getString

class LoginCompanyEntry implements MenuCommand {

    private final CompanyService companyService
    private final MenuFactory menuFactory

    private Company company

    LoginCompanyEntry(CompanyService companyService, MenuFactory menuFactory) {
        ParamValidation.requireNonNull(companyService, "CompanyService cannot be null")

        this.companyService = companyService
        this.menuFactory = menuFactory
    }

    @Override
    void execute() {
        loginCompany()
    }

    @Override
    String getEntryName() {
        return null
    }

    @Override
    MenuState getNextMenuState(MenuState currentState) {
        if (company != null) {
            return menuFactory.createCompanyMenu(company)
        }

        return currentState
    }

    private void loginCompany() {
        Scanner scanner = new Scanner(System.in)

        println "***** Login de Empresa *****"
        println "Digite o email: "
        String email = getString(scanner)

        println "Digite a senha: "
        String password = getString(scanner)

        Company retrievedCompany = companyService.authenticate(email, password)

        if (company != null) {
            println "Login bem-sucedido! Bem-vindo, ${company.name}!"

            company = retrievedCompany

        } else {
            println "Falha no login. Verifique suas credenciais."
        }
    }
}
