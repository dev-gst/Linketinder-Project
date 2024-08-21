package linketinder_test.ui

import Linketinder.models.entities.Address
import Linketinder.models.entities.Company
import Linketinder.models.enums.SkillEnum
import Linketinder.ui.CompanyBuilderMenu
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class CompanyBuilderMenuTest {
    ByteArrayInputStream testIn
    ByteArrayOutputStream testOut
    String input
    String output

    @BeforeEach
    void initVariablesAndStartStream() {
        this.testOut = new ByteArrayOutputStream()
        System.setOut(new PrintStream(testOut))

        input = "Some Company\n" +
                "123456789\n" +
                "The best company\n" +
                "company@example.com\n" +
                "Brazil\n" +
                "DF\n" +
                "1234567890\n" +
                "java,spring boot,groovy"

        output = "Enter company's name " +
                "Enter company's CNPJ " +
                "Enter company's description " +
                "Enter company's email " +
                "Enter company's Country " +
                "Enter company's State " +
                "Enter company's Zip Code " +
                "Enter required skills (separated by comma) "
    }

    @Test
    void createOutputsCorrectMessages() {
        testIn = new ByteArrayInputStream(input.getBytes())
        System.setIn(testIn)

        CompanyBuilderMenu.create()

        assertEquals(output, testOut.toString())
    }

    @Test
    void createReturnsCorrectResult() {
        testIn = new ByteArrayInputStream(input.getBytes())
        System.setIn(testIn)

        Company testCompany = CompanyBuilderMenu.create()

        Address address1 = new Address()
        address1.country = "Brazil"
        address1.state = "DF"
        address1.zipCode = "1234567890"

        SkillEnum[] skills = new SkillEnum[] {
                SkillEnum.JAVA,
                SkillEnum.SPRING_BOOT,
                SkillEnum.GROOVY
        }

        Company company = new Company()
        company.name = "Some Company"
        company.description = "The best company"
        company.email = "company@example.com"
        company.CNPJ = "123456789"
        company.addSkill(skills)
        company.address = address1

        assertEquals(company, testCompany)
    }
}
