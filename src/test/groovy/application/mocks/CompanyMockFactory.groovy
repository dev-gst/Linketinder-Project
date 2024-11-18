package application.mocks

import application.models.dtos.request.CompanyDTO
import application.models.entities.Company
import spock.lang.Specification

class CompanyMockFactory extends Specification {

    CompanyDTO createCompanyDTOMock(int n) {
        CompanyDTO companyDTO = Mock(CompanyDTO)

        companyDTO.name >> "Company $n"
        companyDTO.loginDetailsDTO >> new LoginDetailsMockFactory().createLoginDetailsDTOMock(n)
        companyDTO.description >> "Company description $n"
        companyDTO.cnpj >> "12345678901234 $n"
        companyDTO.addressId >> n

        return companyDTO
    }

    Company createCompanyMock(int n) {
        Company company = Mock(Company)

        company.id >> n
        company.loginDetails >> new LoginDetailsMockFactory().createLoginDetailsMock(n)
        company.name >> "Company $n"
        company.description >> "Company description $n"
        company.cnpj >> "12345678901234 $n"
        company.addressId >> n

        return company
    }
}
