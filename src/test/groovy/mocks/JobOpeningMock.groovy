package mocks

import main.models.dtos.request.JobOpeningDTO
import main.models.entities.JobOpening
import spock.lang.Specification

class JobOpeningMock extends Specification {

    JobOpeningDTO createJobOpeningDTOMock(int n) {
        JobOpeningDTO jobOpeningDTO = Mock(JobOpeningDTO)

        jobOpeningDTO.name >> "name$n"
        jobOpeningDTO.description >> "description$n"
        jobOpeningDTO.isOpen >> true
        jobOpeningDTO.isRemote >> false
        jobOpeningDTO.companyId >> Optional.of(n)

        return jobOpeningDTO
    }

    JobOpening createJobOpeningMock(int n) {
        JobOpening jobOpening = Mock(JobOpening)

        jobOpening.id >> n
        jobOpening.name >> "name$n"
        jobOpening.description >> "description$n"
        jobOpening.isOpen >> true
        jobOpening.isRemote >> false
        jobOpening.companyId >> Optional.of(n)

        return jobOpening
    }
}