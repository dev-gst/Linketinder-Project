package mocks

import main.models.dtos.request.CompanyDTO
import main.models.entities.Company
import spock.lang.Specification

class CompanyMock extends Specification {

    CompanyDTO createCompanyDTOMock(int n) {
        CompanyDTO companyDTO = Mock(CompanyDTO)

        companyDTO.name >> "Company $n"
        companyDTO.loginDetailsDTO >> new LoginDetailsMock().createLoginDetailsDTOMock(n)
        companyDTO.description >> "Company description $n"
        companyDTO.cnpj >> "12345678901234 $n"
        companyDTO.addressId >> n

        return companyDTO
    }

    Company createCompanyMock(int n) {
        Company company = Mock(Company)

        company.id >> n
        company.loginDetails >> new LoginDetailsMock().createLoginDetailsMock(n)
        company.name >> "Company $n"
        company.description >> "Company description $n"
        company.cnpj >> "12345678901234 $n"
        company.addressId >> n

        return company
    }
}
