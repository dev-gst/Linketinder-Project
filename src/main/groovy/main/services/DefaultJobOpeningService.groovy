package main.services

import main.models.dtos.request.JobOpeningDTO
import main.models.entities.Company
import main.models.entities.JobOpening
import main.repositories.interfaces.JobOpeningDAO
import main.services.interfaces.JobOpeningService
import main.util.exception.ParamValidation

class DefaultJobOpeningService implements JobOpeningService {

    JobOpeningDAO jobOpeningDAO

    DefaultJobOpeningService(JobOpeningDAO jobOpeningDAO) {
        ParamValidation.requireNonNull(jobOpeningDAO, "jobOpeningDAO must not be null")

        this.jobOpeningDAO = jobOpeningDAO
    }

    @Override
    JobOpening getById(int id) {
        ParamValidation.requirePositive(id, "id must be positive")

        return jobOpeningDAO.getById(id)
    }

    @Override
    Set<JobOpening> getByField(String fieldName, String fieldValue) {
        ParamValidation.requireNonBlank(fieldName, "fieldName must not be null or blank")
        ParamValidation.requireNonBlank(fieldValue, "fieldValue must not be null or blank")

        return jobOpeningDAO.getByField(fieldName, fieldValue)
    }

    @Override
    Set<JobOpening> getByEntityId(int entityId, Class<?> entityClazz) {
        ParamValidation.requirePositive(entityId, "entityId must be positive")
        ParamValidation.requireNonNull(entityClazz, "entityClazz must not be null")

        switch (entityClazz) {
            case Company.class:
                return jobOpeningDAO.getByCompanyId(entityId)
            default:
                throw new ClassNotFoundException("Class not found for this context")
        }
    }

    @Override
    Set<JobOpening> getAll() {
        return jobOpeningDAO.getAll()
    }

    @Override
    int save(JobOpeningDTO jobOpeningDTO) {
        ParamValidation.requireNonNull(jobOpeningDTO, "jobOpening must not be null")

        return jobOpeningDAO.save(jobOpeningDTO)
    }

    @Override
    Set<Integer> saveAll(Set<JobOpeningDTO> jobOpeningDTOs) {
        ParamValidation.requireNonNull(jobOpeningDTOs, "jobOpeningDTOs must not be null")

        Set<Integer> ids = new LinkedHashSet<>()
        for (JobOpeningDTO jobOpeningDTO : jobOpeningDTOs) {
            ids.add(Integer.valueOf(jobOpeningDAO.save(jobOpeningDTO)))
        }

        return ids
    }

    @Override
    JobOpening updateById(int id, JobOpeningDTO jobOpeningDTO) {
        ParamValidation.requirePositive(id, "id must be positive")
        ParamValidation.requireNonNull(jobOpeningDTO, "jobOpening must not be null")

        return jobOpeningDAO.update(id, jobOpeningDTO)
    }

    @Override
    void deleteById(int id) {
        ParamValidation.requirePositive(id, "id must be positive")

        jobOpeningDAO.delete(id)
    }
}
