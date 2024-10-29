package main.repositories.interfaces

import main.models.dtos.request.JobOpeningDTO
import main.models.entities.JobOpening

interface JobOpeningDAO extends DAO<JobOpening, JobOpeningDTO> {

    Set<JobOpening> getByCompanyId(int companyId)
}