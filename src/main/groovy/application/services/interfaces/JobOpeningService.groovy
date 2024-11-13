package application.services.interfaces

import application.models.dtos.request.JobOpeningDTO
import application.models.entities.JobOpening

interface JobOpeningService extends SearchableService<JobOpening, JobOpeningDTO> {

}