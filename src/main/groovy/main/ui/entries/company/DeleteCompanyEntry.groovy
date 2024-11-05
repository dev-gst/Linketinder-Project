package main.ui.entries.company

import main.models.entities.Company
import main.services.interfaces.CompanyService
import main.ui.interfaces.MenuCommand
import main.ui.interfaces.MenuState
import main.util.exception.ParamValidation

import static main.ui.helpers.UserInputCollector.getBoolean

class DeleteCompanyEntry implements MenuCommand {

    private final Company company
    private final CompanyService companyService

    private boolean confirmation = false

    DeleteCompanyEntry(Company company, CompanyService companyService) {
        ParamValidation.requireNonNull(company, "Company cannot be null")
        ParamValidation.requireNonNull(companyService, "Company service cannot be null")

        this.company = company
        this.companyService = companyService
    }

    @Override
    void execute() {
        deleteProfile()
    }

    @Override
    String getEntryName() {
        return "Deletar Empresa"
    }

    @Override
    MenuState getNextMenuState(MenuState currentState) {
        if (confirmation) {
            return null
        }

        return currentState
    }

    private void deleteProfile() {
        Scanner scanner = new Scanner(System.in)
        println "Tem certeza que deseja deletar o perfil? (s/N)"
        confirmation = getBoolean(scanner)

        if (confirmation) {
            companyService.deleteById(company.id)
            println "Perfil deletado com sucesso!"
        }

        println "Ação cancelada."
    }
}
