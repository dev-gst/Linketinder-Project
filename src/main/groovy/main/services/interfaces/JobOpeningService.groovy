package main.services.interfaces

import main.models.dtos.request.jobOpenings.JobOpeningDTO
import main.models.entities.JobOpening

interface JobOpeningService extends SearchableService<JobOpening, JobOpeningDTO> {

}