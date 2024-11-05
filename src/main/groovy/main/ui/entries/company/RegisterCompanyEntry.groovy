package main.ui.entries.company

import main.models.dtos.request.AddressDTO
import main.models.dtos.request.CompanyDTO
import main.models.dtos.request.login.LoginDetailsDTO
import main.services.interfaces.AddressService
import main.services.interfaces.CompanyService
import main.ui.helpers.UserInfoCollector
import main.ui.interfaces.MenuCommand
import main.ui.interfaces.MenuState
import main.util.exception.ParamValidation

import static main.ui.helpers.UserInputCollector.getString

class RegisterCompanyEntry implements MenuCommand {

    CompanyService companyService
    AddressService addressService

    RegisterCompanyEntry(CompanyService companyService, AddressService addressService) {
        ParamValidation.requireNonNull(companyService, "CompanyService cannot be null")
        ParamValidation.requireNonNull(addressService, "AddressService cannot be null")

        this.companyService = companyService
        this.addressService = addressService
    }

    @Override
    void execute() {
        registerCompany()
    }

    @Override
    String getEntryName() {
        return "Registrar Empresa"
    }

    @Override
    MenuState getNextMenuState(MenuState currentState) {
        return currentState
    }

    // TODO: needs to be refactored later
    private void registerCompany() {
        Scanner scanner = new Scanner(System.in)

        print "Insira o nome da empresa: "
        String name = getString(scanner)

        print "Insira o email: "
        String email = getString(scanner)

        print "Insira a senha: "
        String password = getString(scanner)

        print "Insira a descrição: "
        String description = getString(scanner)

        print "Insira o CNPJ: "
        String cnpj = getString(scanner)

        Map<String, String> addressInfo = UserInfoCollector.gatherAddressInfo(scanner)
        AddressDTO addressDTO = AddressDTO.of(addressInfo)
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
}
