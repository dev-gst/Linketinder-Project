package application.ui.entries.candidate

import application.models.dtos.request.AddressDTO
import application.models.dtos.request.CandidateDTO
import application.services.interfaces.AddressService
import application.services.interfaces.CandidateService
import application.services.interfaces.SkillService
import application.ui.helpers.UserInfoCollector
import spock.lang.Specification

class RegisterCandidateEntryTest extends Specification {

    CandidateService candidateService
    SkillService skillService
    AddressService addressService
    RegisterCandidateEntry registerCandidateEntry

    void setup() {
        candidateService = Mock(CandidateService.class)
        skillService = Mock(SkillService.class)
        addressService = Mock(AddressService.class)

        registerCandidateEntry = new RegisterCandidateEntry(candidateService, skillService, addressService)

        GroovyMock(UserInfoCollector.class, global: true)
    }

    def "execute registers candidate profile successfully"() {
        given:
        UserInfoCollector.gatherCandidateInfo(_ as Scanner) >> [
                firstName  : "John",
                lastName   : "Doe",
                email      : "john.doe@example.com",
                password   : "password",
                cpf        : "12345678900",
                birthDate  : "2000-01-01",
                description: "BIO",
                education  : "Bachelor",
                addressId  : 1
        ]
        UserInfoCollector.gatherAddressInfo(_ as Scanner) >> [
                country     : "Brazil",
                region      : "SP",
                city        : "Sao Paulo",
                neighborhood: "Centro",
                street      : "Paulista",
                number      : "123",
                zipCode     : "01000-000"
        ]

        Set<String> skills = ["Java", "Groovy"] as Set
        Set<Integer> skillIds = [1, 2] as Set

        UserInfoCollector.gatherSkills(_ as Scanner) >> skills
        addressService.save(_ as AddressDTO) >> 1
        candidateService.save(_ as CandidateDTO) >> 1
        skillService.saveAll(_ as Set) >> skillIds

        when:
        registerCandidateEntry.execute()

        then:
        1 * addressService.save(_ as AddressDTO) >> 1
        1 * candidateService.save(_ as CandidateDTO) >> 1
        1 * skillService.saveAll(_ as Set) >> skillIds
        1 * skillService.saveCandidateSkill(1, 1)
        1 * skillService.saveCandidateSkill(1, 2)
    }


    def "entry name is 'Cadastrar perfil'"() {
        expect:
        registerCandidateEntry.getEntryName() == "Cadastrar perfil"
    }
}
