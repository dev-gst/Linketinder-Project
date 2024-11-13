package application.ui.entries.candidate

import application.models.entities.Candidate
import application.services.interfaces.AddressService
import application.services.interfaces.CandidateService
import application.services.interfaces.SkillService
import application.ui.helpers.UserInfoCollector
import mocks.CandidateMock
import spock.lang.Specification

class UpdateCandidateEntryTest extends Specification {

    CandidateService candidateService
    SkillService skillService
    AddressService addressService
    Candidate mockedCandidate
    UpdateCandidateEntry updateCandidateEntry

    void setup() {
        candidateService = Mock(CandidateService.class)
        skillService = Mock(SkillService.class)
        addressService = Mock(AddressService.class)

        mockedCandidate = new CandidateMock().createCandidateMock(1)
        GroovyMock(UserInfoCollector, global: true)

        updateCandidateEntry = new UpdateCandidateEntry(mockedCandidate, candidateService, addressService, skillService)
    }


    def "execute updates candidate profile successfully"() {
        given:
        UserInfoCollector.updateCandidateInfo(_ as Candidate, _ as Scanner) >> [
                firstName  : "Jane",
                lastName   : "Doe",
                email      : "jane.doe@example.com",
                password   : "newpassword",
                cpf        : "98765432100",
                birthDate  : "1990-01-01",
                description: "New BIO",
                education  : "Master"
        ]
        UserInfoCollector.updateAddressInfo(_ as Scanner) >> [
                country     : "Brazil",
                region      : "SP",
                city        : "Sao Paulo",
                neighborhood: "Centro",
                street      : "Paulista",
                number      : "123",
                zipCode     : "01000-000"
        ]

        Set<String> skills = ["Java", "Groovy"] as Set
        UserInfoCollector.updateSkills(_ as Scanner) >> skills
        Set<Integer> skillIds = [1, 2] as Set
        skillService.saveAll(_ as Set) >> skillIds

        when:
        updateCandidateEntry.execute()

        then:
        1 * addressService.updateById(_, _)
        1 * candidateService.updateById(_, _) >> mockedCandidate
        1 * skillService.deleteAllCandidateSkills(_)
        1 * skillService.saveAll(_) >> skillIds
        2 * skillService.saveCandidateSkill(_, _)
    }

    def "execute handles null candidate info"() {
        given:
        UserInfoCollector.updateCandidateInfo(_ as Candidate, _ as Scanner) >> null

        when:
        updateCandidateEntry.execute()

        then:
        0 * addressService.updateById(_, _)
        0 * candidateService.updateById(_, _)
        0 * skillService.deleteAllCandidateSkills(_)
        0 * skillService.saveAll(_)
        0 * skillService.saveCandidateSkill(_, _)
    }

    def "execute handles null address info"() {
        given:
        UserInfoCollector.updateCandidateInfo(_ as Candidate, _ as Scanner) >> [
                firstName  : "Jane",
                lastName   : "Doe",
                email      : "jane.doe@example.com",
                password   : "newpassword",
                cpf        : "98765432100",
                birthDate  : "1990-01-01",
                description: "New BIO",
                education  : "Master"
        ]

        Set<String> skills = ["Java", "Groovy"] as Set
        UserInfoCollector.updateSkills(_ as Scanner) >> skills
        Set<Integer> skillIds = [1, 2] as Set
        skillService.saveAll(_ as Set) >> skillIds

        when:
        updateCandidateEntry.execute()

        then:
        0 * addressService.updateById(_, _)
        1 * candidateService.updateById(_, _) >> mockedCandidate
        1 * skillService.deleteAllCandidateSkills(_)
        1 * skillService.saveAll(_) >> skillIds
        2 * skillService.saveCandidateSkill(_, _)
    }

    def "execute handles null skills"() {
        given:
        UserInfoCollector.updateCandidateInfo(_ as Candidate, _ as Scanner) >> [
                firstName  : "Jane",
                lastName   : "Doe",
                email      : "jane.doe@example.com",
                password   : "newpassword",
                cpf        : "98765432100",
                birthDate  : "1990-01-01",
                description: "New BIO",
                education  : "Master"
        ]
        UserInfoCollector.updateAddressInfo(_ as Scanner) >> [
                country     : "Brazil",
                region      : "SP",
                city        : "Sao Paulo",
                neighborhood: "Centro",
                street      : "Paulista",
                number      : "123",
                zipCode     : "01000-000"
        ]

        UserInfoCollector.updateSkills(_ as Scanner) >> null

        when:
        updateCandidateEntry.execute()

        then:
        1 * addressService.updateById(_, _)
        1 * candidateService.updateById(_, _) >> mockedCandidate
        0 * skillService.deleteAllCandidateSkills(_)
        0 * skillService.saveAll(_)
        0 * skillService.saveCandidateSkill(_, _)
    }

    def "getEntryName returns correct entry name"() {
        expect:
        updateCandidateEntry.getEntryName() == "Atualizar perfil"
    }
}