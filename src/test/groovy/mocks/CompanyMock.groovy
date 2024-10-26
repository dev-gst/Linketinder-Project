package mocks


import main.models.dtos.request.company.CompanyDTO
import main.models.dtos.request.company.CompanyDetailsDTO
import main.models.entities.company.Company
import main.models.entities.company.CompanyDetails
import spock.lang.Specification

class CompanyMock extends Specification {

    CompanyDTO createCompanyDTOMock(int n) {
        CompanyDTO companyDTO = Mock(CompanyDTO)

        companyDTO.companyDetailsDTO >> createCompanyDetailsDTOMock(n)
        companyDTO.loginDetails >> new LoginDetailsMock().createLoginDetailsDTOMock(n)

        return companyDTO
    }

    CompanyDetailsDTO createCompanyDetailsDTOMock(int n) {
        CompanyDetailsDTO companyDetailsDTO = Mock(CompanyDetailsDTO)

        companyDetailsDTO.name >> "Company $n"
        companyDetailsDTO.description >> "Company description $n"
        companyDetailsDTO.cnpj >> "12345678901234 $n"
        companyDetailsDTO.addressDTO >> new AddressMock().createAddressDTOMock(n)

        return companyDetailsDTO
    }

    Company createCompanyMock(int n) {
        Company company = Mock(Company)

        company.id >> n
        company.companyDetails >> createCompanyDetailsMock(n)
        company.loginDetails >> new LoginDetailsMock().createLoginDetailsMock(n)

        return company
    }

    CompanyDetails createCompanyDetailsMock(int n) {
        CompanyDetails companyDetails = Mock(CompanyDetails)

        companyDetails.name >> "Company $n"
        companyDetails.description >> "Company description $n"
        companyDetails.cnpj >> "12345678901234 $n"
        companyDetails.address >> new AddressMock().createAddressMock(n)

        return companyDetails
    }
}
