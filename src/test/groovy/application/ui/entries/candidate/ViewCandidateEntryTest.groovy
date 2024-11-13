package application.ui.entries.candidate

import application.models.entities.Address
import application.models.entities.Candidate
import application.models.entities.Skill
import application.services.interfaces.AddressService
import application.services.interfaces.SkillService
import mocks.AddressMockFactory
import mocks.CandidateMockFactory
import mocks.SkillMockFactory
import spock.lang.Specification

import java.time.LocalDate
import java.time.ZoneId

class ViewCandidateEntryTest extends Specification {

    Candidate mockedCandidate
    Address mockedAddress
    Set<Skill> mockedSkills
    SkillService mockedSkillService
    AddressService mockedAddressService

    ByteArrayOutputStream testOut

    ViewCandidateEntry viewCandidateProfile

    def setup() {
        testOut = new ByteArrayOutputStream()
        System.setOut(new PrintStream(testOut))

        Skill skill1 = new SkillMockFactory().createSkillMock(1)
        Skill skill2 = new SkillMockFactory().createSkillMock(2)

        mockedCandidate = new CandidateMockFactory().createCandidateMock(1)
        mockedAddress = new AddressMockFactory().createAddressMock(1)
        mockedSkills = Set.of(skill1, skill2)

        mockedSkillService = Mock(SkillService.class)
        mockedAddressService = Mock(AddressService.class)

        viewCandidateProfile = new ViewCandidateEntry(mockedCandidate, mockedSkillService, mockedAddressService)
    }

    def "should display candidate profile"() {
        given:
        def output = "Candidate profile\n" +
                "Name: ${mockedCandidate.firstName} ${mockedCandidate.lastName}\n" +
                "Email: ${mockedCandidate.loginDetails.email}\n" +
                "Description: ${mockedCandidate.description}\n" +
                "Birth date: ${LocalDate.ofInstant(mockedCandidate.birthDate, ZoneId.systemDefault())}\n" +
                "CPF: ${mockedCandidate.cpf}\n" +
                "Education: ${mockedCandidate.education}\n" +
                "Skills: ${mockedSkills.toString()}\n" +
                "Address: ${mockedAddress.toString()}\n"

        mockedSkillService.getByEntityId(mockedCandidate.id, Candidate.class) >> mockedSkills
        mockedAddressService.getByEntityId(mockedCandidate.id, Candidate.class) >> Set.of(mockedAddress)

        when:
        viewCandidateProfile.execute()

        then:
        testOut.toString() == output
    }

    def "should throw exception when candidate is null"() {
        when:
        new ViewCandidateEntry(null, mockedSkillService, mockedAddressService)

        then:
        thrown(IllegalArgumentException)
    }

    def "should throw exception when skills is null"() {
        when:
        new ViewCandidateEntry(mockedCandidate, null, mockedAddressService)

        then:
        thrown(IllegalArgumentException)
    }

    def "should throw exception when address is null"() {
        when:
        new ViewCandidateEntry(mockedCandidate, mockedSkillService, null)

        then:
        thrown(IllegalArgumentException)
    }

    def cleanup() {
        System.setOut(System.out)
    }
}
