package main.ui.entries.candidate

import main.models.entities.Candidate
import main.services.interfaces.CandidateService
import main.ui.helpers.UserInputCollector
import mocks.CandidateMock
import spock.lang.Specification

class DeleteCandidateEntryTest extends Specification {

    CandidateService candidateService
    Candidate candidate
    DeleteCandidateEntry deleteCandidateEntry

    void setup() {
        CandidateMock candidateMock = new CandidateMock()

        GroovyMock(UserInputCollector, global: true)

        candidate = candidateMock.createCandidateMock(1)
        candidateService = Mock(CandidateService)
        deleteCandidateEntry = new DeleteCandidateEntry(candidate, candidateService)
    }

    def "execute deletes candidate profile when confirmed"() {
        given:
        UserInputCollector.getBoolean(_ as Scanner) >> true

        when:
        deleteCandidateEntry.execute()

        then:
        1 * candidateService.deleteById(candidate.id)
    }

    def "execute does not delete candidate profile when not confirmed"() {
        given:
        UserInputCollector.getBoolean(_ as Scanner) >> false

        when:
        deleteCandidateEntry.execute()

        then:
        0 * candidateService.deleteById(_)
    }

    def "getEntryName returns correct entry name"() {
        expect:
        deleteCandidateEntry.getEntryName() == "Deletar conta"
    }
}
