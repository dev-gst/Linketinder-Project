package main.models.dtos.request.jobOpenings

import main.util.exception.ParamValidation

class JobOpeningDTO {

    final JobOpeningDetailsDTO jobOpeningDetailsDTO
    final int companyId

    JobOpeningDTO(JobOpeningDetailsDTO jobOpeningDetailsDTO, int companyId) {
        ParamValidation.requireNonNull(jobOpeningDetailsDTO, "JobOpeningDetailsDTO cannot be null")
        ParamValidation.requirePositive(companyId, "companyId must be positive")

        this.jobOpeningDetailsDTO = jobOpeningDetailsDTO
        this.companyId = companyId
    }
}
