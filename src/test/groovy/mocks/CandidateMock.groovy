package mocks

import main.models.dtos.request.CandidateDTO
import main.models.entities.Candidate
import spock.lang.Specification

import java.time.LocalDate
import java.time.ZoneOffset

class CandidateMock extends Specification {

    Candidate createCandidateMock(int n) {
        Candidate candidate = Mock(Candidate.class)
        candidate.id >> n
        candidate.firstName >> "Candidate $n"
        candidate.lastName >> "Last Name $n"
        candidate.loginDetails >> new LoginDetailsMock().createLoginDetailsMock(n)
        candidate.birthDate >> LocalDate.parse("1990-01-01")
                .atStartOfDay().toInstant(ZoneOffset.UTC)
        candidate.description >> "Description $n"
        candidate.cpf >> "123456789$n"
        candidate.education >> "Education $n"
        candidate.addressId >> n

        return candidate
    }

    CandidateDTO createCandidateDTOMock(int n) {
        CandidateDTO candidateDTO = Mock(CandidateDTO.class)
        candidateDTO.firstName >> "Candidate $n"
        candidateDTO.lastName >> "Last Name $n"
        candidateDTO.loginDetailsDTO >> new LoginDetailsMock().createLoginDetailsDTOMock(n)
        candidateDTO.birthDate >> LocalDate.parse("1990-01-01")
        candidateDTO.description >> "Description $n"
        candidateDTO.cpf >> "123456789$n"
        candidateDTO.education >> "Education $n"
        candidateDTO.addressId >> n

        return candidateDTO
    }
}
