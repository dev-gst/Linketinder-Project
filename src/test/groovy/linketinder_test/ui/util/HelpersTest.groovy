package linketinder_test.ui.util

import Linketinder.ui.util.Helpers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class HelpersTest {
    ByteArrayInputStream testIn
    ByteArrayOutputStream testOut

    @BeforeEach
    void initVariablesAndStartStream() {
        this.testOut = new ByteArrayOutputStream()
        System.setOut(new PrintStream(testOut))
    }

    @Test
    void getFieldFromUsrAcceptOnlyValidStrings() {
        String input = "\nworks\n"
        testIn = new ByteArrayInputStream(input.getBytes())
        System.setIn(testIn)

        String testInput = Helpers.getFieldFromUsr(new Scanner(System.in))

        assertEquals("Invalid input, try again!\n", testOut.toString())
        assertEquals("works", testInput)
    }

    @Test
    void getAgeFromUsrAcceptsOnlyValidInteger() {
        String input = "abc\n15\n126\n25\n"
        testIn = new ByteArrayInputStream(input.getBytes())
        System.setIn(testIn)

        int testInput = Helpers.getAgeFromUsr(new Scanner(System.in))

        String expectedOutput = "Invalid age, try again!\n" +
                                "Invalid age, try again!\n" +
                                "Invalid age, try again!\n"
        assertEquals(expectedOutput, testOut.toString())
        assertEquals(25, testInput)
    }

    @Test
    void getSkillsFromUsrAcceptsOnlyValidSkills() {
        String input = "abc\n15\n126\n25\n"
        testIn = new ByteArrayInputStream(input.getBytes())
        System.setIn(testIn)

        int testInput = Helpers.getAgeFromUsr(new Scanner(System.in))

        String expectedOutput = "Invalid age, try again!\n" +
                "Invalid age, try again!\n" +
                "Invalid age, try again!\n"
        assertEquals(expectedOutput, testOut.toString())
        assertEquals(25, testInput)
    }
}
