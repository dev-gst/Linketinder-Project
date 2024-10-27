package main.repositories.interfaces

import main.models.dtos.request.jobOpenings.JobOpeningDTO
import main.models.entities.jobOpening.JobOpening

interface JobOpeningDAO extends DAO<JobOpening, JobOpeningDTO> {

    Set<JobOpening> getByCompanyId(int companyId)
}