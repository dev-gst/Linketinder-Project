package linketinder_test.services

import Linketinder.models.entities.Address
import Linketinder.models.entities.Company
import Linketinder.models.enums.SkillEnum
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import Linketinder.services.CompanyService

import static org.junit.jupiter.api.Assertions.*
import static org.mockito.Mockito.*

class CompanyServiceTest {

    CompanyService companyService

    @BeforeEach
    void initializeVariables() {
        companyService = new CompanyService()
    }

    @Test
    void populateWorks() {
        companyService.populate()

        Address address1 = mock(Address.class)
        when(address1.country).thenReturn("Brazil")
        when(address1.state).thenReturn("DF")
        when(address1.zipCode).thenReturn("1234567890")

        Address address2 = mock(Address.class)
        when(address2.country).thenReturn("Brazil")
        when(address2.state).thenReturn("GO")
        when(address2.zipCode).thenReturn("9876543214")

        Company company1 = mock(Company.class)
        when(company1.ID).thenReturn(1.toBigInteger())
        when(company1.name).thenReturn("Company Name0")
        when(company1.description).thenReturn("Good company")
        when(company1.email).thenReturn("0@example.com")
        when(company1.address).thenReturn(address1)
        when(company1.CNPJ).thenReturn("000000000000")
        when(company1.skills)
                .thenReturn(new LinkedHashSet<>(Arrays
                        .asList(SkillEnum.JAVA, SkillEnum.SPRING_BOOT, SkillEnum.GROOVY)))

        Company company2 = mock(Company.class)
        when(company2.ID).thenReturn(5.toBigInteger())
        when(company2.name).thenReturn("Company Name4")
        when(company2.description).thenReturn("Bad company")
        when(company2.email).thenReturn("4@example.com")
        when(company2.address).thenReturn(address2)
        when(company2.CNPJ).thenReturn("000000000004")
        when(company2.skills)
                .thenReturn(new LinkedHashSet<>(Arrays
                        .asList(SkillEnum.ANGULAR, SkillEnum.JAVASCRIPT)))

        assertEquals(companyService.companies.get(0), company1)
        assertEquals(companyService.companies.get(4), company2)
    }

    @Test
    void addWorks() {
        Company company = mock(Company.class)
        companyService.add(company)

        assertEquals(companyService.companies.get(0), company)
    }
}
