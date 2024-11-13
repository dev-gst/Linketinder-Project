package application.ui.entries.common

import spock.lang.Specification

class BackEntryTest extends Specification {

    ByteArrayOutputStream testOut

    BackEntry backEntry

    void setup() {
        testOut = new ByteArrayOutputStream()
        System.setOut(new PrintStream(testOut))

        backEntry = new BackEntry()
    }

    void "test getEntryName"() {
        expect:
        backEntry.getEntryName() == "Voltar"
    }

    void "test execute"() {
        given:
        String expectedOutput = "Voltando...\n"

        when:
        backEntry.execute()

        then:
        testOut.toString() == expectedOutput
    }

    void cleanup() {
        System.setOut(System.out)
    }
}
