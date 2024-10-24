package mocks

import main.models.dtos.request.company.CompanyDTO
import main.models.dtos.request.company.CompanyDetailsDTO
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
}
