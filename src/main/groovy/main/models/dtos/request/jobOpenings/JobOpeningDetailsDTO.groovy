package main.models.dtos.request.jobOpenings

import main.util.exception.ParamValidation

class JobOpeningDetailsDTO {

    final String name
    final String description
    final boolean isOpen
    final JobOpeningAddressDTO jobOpeningAddressDTO

    JobOpeningDetailsDTO(String name, String description, boolean isOpen, JobOpeningAddressDTO jobOpeningAddressDTO) {
        ParamValidation.requireNonBlank(name, "Name cannot be null or blank")
        ParamValidation.requireNonBlank(description, "Description cannot be null or blank")
        ParamValidation.requireNonNull(jobOpeningAddressDTO, "JobOpeningAddressDTO cannot be null")

        this.name = name
        this.description = description
        this.isOpen = isOpen
        this.jobOpeningAddressDTO = jobOpeningAddressDTO
    }
}
