package linketinder_test.models.entities

import Linketinder.models.entities.Address
import Linketinder.models.entities.Candidate
import Linketinder.models.enums.SkillEnum
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotEquals
import static org.mockito.Mockito.mock

class CandidateTest {
    static Candidate candidate1
    static Candidate candidate2
    static Candidate candidate3
    static Address address1
    static Address address2

    @BeforeEach
    void initializeVariables() {
        address1 = mock(Address.class)
        address2 = mock(Address.class)

        candidate1 = new Candidate()
        candidate2 = new Candidate()
        candidate3 = new Candidate()

        candidate1.ID = 1
        candidate1.name = "Marcos"
        candidate1.description = "Good programmer"
        candidate1.email = "candidate1@candidate.com"
        candidate1.age = 18
        candidate1.CPF = "123456789"
        candidate1.skills = new LinkedHashSet<>(Arrays.asList(SkillEnum.JAVA))
        candidate1.address = address1

        candidate2.ID = 2
        candidate2.name = "Pedro"
        candidate2.description = "Good mathematician"
        candidate2.email = "candidate2@candidate.com"
        candidate2.age = 19
        candidate2.CPF = "987654321"
        candidate2.skills = new LinkedHashSet<>(Arrays.asList(SkillEnum.JAVA))
        candidate2.address = address2

        candidate3.ID = 1
        candidate3.name = "Marcos"
        candidate3.description = "Good programmer"
        candidate3.email = "candidate1@candidate.com"
        candidate3.age = 18
        candidate3.CPF = "123456789"
        candidate3.skills = new LinkedHashSet<>(Arrays.asList(SkillEnum.JAVA))
        candidate3.address = address1
    }

    @Test
    void isEquals() {
        assertEquals(candidate1, candidate3)
    }

    @Test
    void isNotEquals() {
        assertNotEquals(candidate1, candidate2)
        assertNotEquals(candidate2, candidate3)
    }

    @Test
    void toStringModel() {
        String expectedResult = "Name: Marcos" +
                "\nDescription: Good programmer" +
                "\nEmail: candidate1@candidate.com" +
                "\nAge: 18" +
                "\nAddress: ${address1.toString()}" +
                "\nSkills: JAVA\n"

        String result = candidate1.toString()

        assertEquals(result, expectedResult)
    }

    @Test
    void addSkillWorks() {
        candidate1.skills = new LinkedHashSet<>()
        candidate2.skills = new LinkedHashSet<>()

        candidate1.addSkill(SkillEnum.JAVA, SkillEnum.ANGULAR, SkillEnum.JAVASCRIPT)
        assertEquals(candidate1.skills[0], SkillEnum.JAVA)
        assertEquals(candidate1.skills[1], SkillEnum.ANGULAR)
        assertEquals(candidate1.skills[2], SkillEnum.JAVASCRIPT)

        candidate2.addSkill(SkillEnum.ANGULAR, SkillEnum.GROOVY, SkillEnum.JAVASCRIPT)
        assertEquals(candidate2.skills[0], SkillEnum.ANGULAR)
        assertEquals(candidate2.skills[1], SkillEnum.GROOVY)
        assertEquals(candidate2.skills[2], SkillEnum.JAVASCRIPT)
    }

    @Test
    void getSFormatedSkillsWorks() {
        candidate1.skills = new LinkedHashSet<>(
                Arrays.asList(SkillEnum.JAVA, SkillEnum.SPRING_BOOT, SkillEnum.JAVASCRIPT))

        String expectedResult = "JAVA, SPRING BOOT, JAVASCRIPT"
        String result = candidate1.getFormatedSkills()

        assertEquals(expectedResult, result)
    }
}
