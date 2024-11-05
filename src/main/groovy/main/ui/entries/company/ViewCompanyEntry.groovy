package main.ui.entries.company


import main.models.entities.Company
import main.services.interfaces.AddressService
import main.ui.interfaces.MenuCommand
import main.ui.interfaces.MenuState
import main.util.exception.ParamValidation

class ViewCompanyEntry implements MenuCommand {

    private final Company company
    private final AddressService addressService

    ViewCompanyEntry(Company company, AddressService addressService) {
        ParamValidation.requireNonNull(company, "Company cannot be null")
        ParamValidation.requireNonNull(addressService, "Address service cannot be null")

        this.company = company
        this.addressService = addressService
    }

    @Override
    void execute() {
        displayCompany()
    }

    @Override
    String getEntryName() {
        return "Visualizar Empresa"
    }

    @Override
    MenuState getNextMenuState(MenuState currentState) {
        return currentState
    }

    private displayCompany() {
        println "Company profile"
        println "Name: ${company.name}"
        println "Email: ${company.loginDetails.email}"
        println "Description: ${company.description}"
        println "CNPJ: ${company.cnpj}"
        println "Address: ${getCompanyAddress()}"
    }

    private String getCompanyAddress() {
        return addressService.getByEntityId(company.id, Company.class)[0]
    }
}
