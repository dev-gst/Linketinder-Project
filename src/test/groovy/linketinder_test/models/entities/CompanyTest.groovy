package linketinder_test.models.entities

import Linketinder.models.entities.Address
import Linketinder.models.entities.Company
import Linketinder.models.enums.SkillEnum
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotEquals
import static org.mockito.Mockito.mock

class CompanyTest {
    static Company company1
    static Company company2
    static Company company3
    static Address address1
    static Address address2

    @BeforeEach
    void initializeVariables() {
        address1 = mock(Address.class)
        address2 = mock(Address.class)

        company1 = new Company()
        company2 = new Company()
        company3 = new Company()

        company1.ID = 1
        company1.name = "Company Name"
        company1.description = "Good Company"
        company1.email = "company1@company.com"
        company1.CNPJ = "123456789"
        company1.skills = new LinkedHashSet<>(Arrays.asList(SkillEnum.JAVA))
        company1.address = address1

        company2.ID = 2
        company2.name = "Company not Name"
        company2.description = "Not Good Company"
        company2.email = "company2@company.com"
        company2.CNPJ = "987654321"
        company2.skills = new LinkedHashSet<>(Arrays.asList(SkillEnum.JAVA))
        company2.address = address2

        company3.ID = 1
        company3.name = "Company Name"
        company3.description = "Good Company"
        company3.email = "company1@company.com"
        company3.CNPJ = "123456789"
        company3.skills = new LinkedHashSet<>(Arrays.asList(SkillEnum.JAVA))
        company3.address = address1
    }

    @Test
    void isEquals() {
        assertEquals(company1, company3)
    }

    @Test
    void isNotEquals() {
        assertNotEquals(company1, company2)
        assertNotEquals(company2, company3)
    }

    @Test
    void toStringModel() {
        String expectedResult = "Name: Company Name" +
                "\nDescription: Good Company" +
                "\nEmail: company1@company.com" +
                "\nCNPJ: 123456789" +
                "\nAddress: ${address1.toString()}" +
            "\nSkills Required: JAVA\n"

        String result = company1.toString()

        assertEquals(result, expectedResult)
    }

    @Test
    void addSkillWorks() {
        company1.skills = new LinkedHashSet<>()
        company2.skills = new LinkedHashSet<>()

        company1.addSkill(SkillEnum.JAVA, SkillEnum.ANGULAR, SkillEnum.JAVASCRIPT)
        assertEquals(company1.skills[0], SkillEnum.JAVA)
        assertEquals(company1.skills[1], SkillEnum.ANGULAR)
        assertEquals(company1.skills[2], SkillEnum.JAVASCRIPT)

        company2.addSkill(SkillEnum.ANGULAR, SkillEnum.GROOVY, SkillEnum.JAVASCRIPT)
        assertEquals(company2.skills[0], SkillEnum.ANGULAR)
        assertEquals(company2.skills[1], SkillEnum.GROOVY)
        assertEquals(company2.skills[2], SkillEnum.JAVASCRIPT)
    }

    @Test
    void getSFormatedSkillsWorks() {
        company1.skills = new LinkedHashSet<>(
                Arrays.asList(SkillEnum.JAVA, SkillEnum.SPRING_BOOT, SkillEnum.JAVASCRIPT))

        String expectedResult = "JAVA, SPRING BOOT, JAVASCRIPT"
        String result = company1.getFormatedSkills()

        assertEquals(expectedResult, result)
    }
}
