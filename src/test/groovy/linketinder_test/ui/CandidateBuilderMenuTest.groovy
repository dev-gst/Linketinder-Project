package linketinder_test.ui

import Linketinder.models.entities.Address
import Linketinder.models.entities.Candidate
import Linketinder.models.enums.SkillEnum
import Linketinder.ui.CandidateBuilderMenu
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class CandidateBuilderMenuTest {

    ByteArrayInputStream testIn
    ByteArrayOutputStream testOut
    String input
    String output

    @BeforeEach
    void initVariablesAndStartStream() {
        this.testOut = new ByteArrayOutputStream()
        System.setOut(new PrintStream(testOut))

        input = "Marcos\n" +
                "25\n" +
                "123456789\n" +
                "The best developer\n" +
                "marcos@example.com\n" +
                "Brazil\n" +
                "DF\n" +
                "1234567890\n" +
                "java,spring boot,groovy"

        output = "Enter applicant's name " +
                "Enter applicant's age " +
                "Enter applicant's CPF " +
                "Enter applicant's description " +
                "Enter applicant's email " +
                "Enter applicant's Country " +
                "Enter applicant's State " +
                "Enter applicant's Zip Code " +
                "Enter applicant's skills (separated by comma) "
    }

    @Test
    void createOutputsCorrectMessages() {
        testIn = new ByteArrayInputStream(input.getBytes())
        System.setIn(testIn)

        CandidateBuilderMenu.create()

        assertEquals(output, testOut.toString())
    }

    @Test
    void createReturnsCorrectResult() {
        testIn = new ByteArrayInputStream(input.getBytes())
        System.setIn(testIn)

        Candidate testCandidate = CandidateBuilderMenu.create()

        Address address1 = new Address()
        address1.country = "Brazil"
        address1.state = "DF"
        address1.zipCode = "1234567890"

        SkillEnum[] skills = new SkillEnum[] {
                SkillEnum.JAVA,
                SkillEnum.SPRING_BOOT,
                SkillEnum.GROOVY
        }

        Candidate candidate = new Candidate()
        candidate.name = "Marcos"
        candidate.description = "The best developer"
        candidate.email = "marcos@example.com"
        candidate.age = 25
        candidate.CPF = "123456789"
        candidate.addSkill(skills)
        candidate.address = address1

        assertEquals(candidate, testCandidate)
    }
}
