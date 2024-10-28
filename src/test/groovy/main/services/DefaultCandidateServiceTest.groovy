package main.services

import main.models.dtos.request.CandidateDTO
import main.models.entities.Address
import main.models.entities.Candidate
import main.repositories.interfaces.CandidateDAO
import main.services.interfaces.CandidateService
import mocks.CandidateMock
import spock.lang.Specification

class DefaultCandidateServiceTest extends Specification {

    Candidate candidateMock1
    Candidate candidateMock2
    CandidateDTO candidateDTOMock1
    CandidateDTO candidateDTOMock2

    CandidateDAO candidateDAO
    CandidateService candidateService


    void setup() {
        CandidateMock candidateMock = new CandidateMock()

        candidateMock1 = candidateMock.createCandidateMock(1)
        candidateMock2 = candidateMock.createCandidateMock(2)
        candidateDTOMock1 = candidateMock.createCandidateDTOMock(1)
        candidateDTOMock2 = candidateMock.createCandidateDTOMock(2)

        candidateDAO = Mock(CandidateDAO)
        candidateService = new DefaultCandidateService(candidateDAO)
    }

    def "get by id should return candidate"() {
        when:
        candidateDAO.getById(1) >> candidateMock1

        then:
        candidateMock1 == candidateService.getById(1)
    }

    def "get by id should throw exception when id is negative"() {
        when:
        candidateService.getById(-1)

        then:
        thrown(IllegalArgumentException)
    }

    def "get by entity id should return candidate set"() {
        given:
        Set<Candidate> candidates = new LinkedHashSet<>()
        candidates.add(candidateMock1)
        candidates.add(candidateMock2)

        when:
        candidateDAO.getByAddressId(1) >> candidates

        then:
        candidates == candidateService.getByEntityId(1, Address.class)
    }

    def "get by entity id should throw exception when id is negative"() {
        when:
        candidateService.getByEntityId(-1, Address.class)

        then:
        thrown(IllegalArgumentException)
    }

    def "get by field should return candidate set"() {
        given:
        Set<Candidate> candidates = new LinkedHashSet<>()
        candidates.add(candidateMock1)
        candidates.add(candidateMock2)
        candidateDAO.getByField("email", "email@email.com") >> candidates

        when:
        Set<Candidate> result = candidateService.getByField("email", "email@email.com")

        then:
        candidates == result
    }

    def "get by field should throw exception when field name is null"() {
        when:
        candidateService.getByField(null, "email@email.com")

        then:
        thrown(IllegalArgumentException)
    }

    def "get all should return candidate set"() {
        given:
        Set<Candidate> candidates = new LinkedHashSet<>()
        candidates.add(candidateMock1)
        candidates.add(candidateMock2)
        candidateDAO.getAll() >> candidates

        when:
        Set<Candidate> result = candidateService.getAll()

        then:
        candidates == result
    }

    def "save should return candidate id"() {
        when:
        candidateDAO.save(candidateDTOMock1) >> 1

        then:
        1 == candidateService.save(candidateDTOMock1)
    }

    def "save all should return candidate ids"() {
        given:
        Set<CandidateDTO> candidateDTOs = new LinkedHashSet<>()
        candidateDTOs.add(candidateDTOMock1)
        candidateDTOs.add(candidateDTOMock2)

        when:
        candidateDAO.save(candidateDTOMock1) >> 1
        candidateDAO.save(candidateDTOMock2) >> 2

        then:
        [1, 2] as LinkedHashSet == candidateService.saveAll(candidateDTOs)
    }

    def "update should return updated candidate"() {
        given:
        candidateDAO.update(1, candidateDTOMock1) >> candidateMock1

        when:
        Candidate result = candidateService.updateById(1, candidateDTOMock1)

        then:
        candidateMock1 == result
    }

    def "delete should call DAO delete"() {
        when:
        candidateService.deleteById(1)

        then:
        1 * candidateDAO.delete(1)
    }

    def "delete should throw exception when id is negative"() {
        when:
        candidateService.deleteById(-1)

        then:
        thrown(IllegalArgumentException)
    }
}
