package main.ui.entries.candidate

import main.models.entities.Address
import main.models.entities.Candidate
import main.models.entities.Skill
import mocks.AddressMock
import mocks.CandidateMock
import mocks.SkillMock
import spock.lang.Specification

import java.time.LocalDate
import java.time.ZoneId

class ViewCandidateEntryTest extends Specification {

    Candidate candidate
    Set<Skill> skills
    Address address

    ByteArrayOutputStream testOut

    ViewCandidateEntry viewCandidateProfile

    def setup() {
        testOut = new ByteArrayOutputStream()
        System.setOut(new PrintStream(testOut))

        Skill skill1 = new SkillMock().createSkillMock(1)
        Skill skill2 = new SkillMock().createSkillMock(2)

        candidate = new CandidateMock().createCandidateMock(1)
        address = new AddressMock().createAddressMock(1)
        skills = new LinkedHashSet<>()
        skills.add(skill1)
        skills.add(skill2)

        viewCandidateProfile = new ViewCandidateEntry(candidate, skills, address)
    }

    def "should display candidate profile"() {
        given:
        def output = "Candidate profile\n" +
                "Name: ${candidate.firstName} ${candidate.lastName}\n" +
                "Email: ${candidate.loginDetails.email}\n" +
                "Description: ${candidate.description}\n" +
                "Birth date: ${LocalDate.ofInstant(candidate.birthDate, ZoneId.systemDefault())}\n" +
                "CPF: ${candidate.cpf}\n" +
                "Education: ${candidate.education}\n" +
                "Skills: ${skills}\n" +
                "Address: ${address}\n"

        when:
        viewCandidateProfile.execute()

        then:
        testOut.toString() == output
    }

    def "should throw exception when candidate is null"() {
        when:
        new ViewCandidateEntry(null, skills, address)

        then:
        thrown(IllegalArgumentException)
    }

    def "should throw exception when skills is null"() {
        when:
        new ViewCandidateEntry(candidate, null, address)

        then:
        thrown(IllegalArgumentException)
    }

    def "should throw exception when address is null"() {
        when:
        new ViewCandidateEntry(candidate, skills, null)

        then:
        thrown(IllegalArgumentException)
    }

    def cleanup() {
        System.setOut(System.out)
    }
}
