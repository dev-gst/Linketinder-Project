package mocks

import main.models.dtos.request.jobOpenings.JobOpeningAddressDTO
import main.models.dtos.request.jobOpenings.JobOpeningDTO
import main.models.dtos.request.jobOpenings.JobOpeningDetailsDTO
import main.models.entities.jobOpening.JobOpening
import main.models.entities.jobOpening.JobOpeningAddress
import main.models.entities.jobOpening.JobOpeningDetails
import spock.lang.Specification

class JobOpeningMock extends Specification {

    JobOpeningDTO createJobOpeningDTOMock(int n) {
        JobOpeningDTO jobOpeningDTO = Mock(JobOpeningDTO)

        jobOpeningDTO.jobOpeningDetailsDTO >> createJobOpeningDetailsDTOMock(n)
        jobOpeningDTO.companyId >> n

        return jobOpeningDTO
    }

    JobOpeningDetailsDTO createJobOpeningDetailsDTOMock(int n) {
        JobOpeningDetailsDTO jobOpeningDetailsDTO = Mock(JobOpeningDetailsDTO)

        jobOpeningDetailsDTO.name >> "Job Opening $n"
        jobOpeningDetailsDTO.description >> "Job Opening description $n"
        jobOpeningDetailsDTO.isOpen >> true
        jobOpeningDetailsDTO.jobOpeningAddressDTO >> createJobOpeningAddressDTOMock(n)

        return jobOpeningDetailsDTO
    }

    JobOpeningAddressDTO createJobOpeningAddressDTOMock(int n) {
        JobOpeningAddressDTO jobOpeningAddressDTO = Mock(JobOpeningAddressDTO)

        jobOpeningAddressDTO.addressId >> n
        jobOpeningAddressDTO.isRemote >> false

        return jobOpeningAddressDTO
    }

    JobOpening createJobOpeningMock(int n) {
        JobOpening jobOpening = Mock(JobOpening)

        jobOpening.id >> n
        jobOpening.jobOpeningDetails >> createJobOpeningDetailsMock(n)
        jobOpening.companyId >> n

        return jobOpening
    }

    JobOpeningDetails createJobOpeningDetailsMock(int n) {
        JobOpeningDetails jobOpeningDetails = Mock(JobOpeningDetails)

        jobOpeningDetails.name >> "Job Opening $n"
        jobOpeningDetails.description >> "Job Opening description $n"
        jobOpeningDetails.isOpen >> true
        jobOpeningDetails.jobOpeningAddress >> createJobOpeningAddressMock(n)

        return jobOpeningDetails
    }

    JobOpeningAddress createJobOpeningAddressMock(int n) {
        JobOpeningAddress jobOpeningAddress = Mock(JobOpeningAddress)

        jobOpeningAddress.addressId >> n
        jobOpeningAddress.isRemote >> false

        return jobOpeningAddress
    }
}