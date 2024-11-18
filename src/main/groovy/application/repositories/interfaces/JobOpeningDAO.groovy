package application.repositories.interfaces

import application.models.dtos.request.JobOpeningDTO
import application.models.entities.JobOpening

interface JobOpeningDAO extends DAO<JobOpening, JobOpeningDTO> {

    Set<JobOpening> getByCompanyId(int companyId)
}