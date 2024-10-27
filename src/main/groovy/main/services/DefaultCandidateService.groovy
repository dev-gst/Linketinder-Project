package main.services

import main.models.dtos.request.CandidateDTO
import main.models.entities.Candidate
import main.models.entities.address.Address
import main.repositories.interfaces.CandidateDAO
import main.services.interfaces.CandidateService
import main.util.exception.ParamValidation

class DefaultCandidateService implements CandidateService {

    CandidateDAO candidateDAO

    DefaultCandidateService(CandidateDAO candidateDAO) {
        this.candidateDAO = candidateDAO
    }

    @Override
    Candidate getById(int id) {
        ParamValidation.requirePositive(id, "id must be positive")

        return candidateDAO.getById(id)
    }

    @Override
    Set<Candidate> getByField(String fieldName, String fieldValue) {
        ParamValidation.requireNonNull(fieldName, "fieldName must not be null")
        ParamValidation.requireNonNull(fieldValue, "fieldValue must not be null")

        return candidateDAO.getByField(fieldName, fieldValue)
    }

    @Override
    Set<Candidate> getByEntityId(int entityId, Class<?> entityClazz) {
        ParamValidation.requirePositive(entityId, "entityId must be positive")
        ParamValidation.requireNonNull(entityClazz, "entityClazz must not be null")

        switch (entityClazz) {
            case Address.class:
                return candidateDAO.getByAddressId(entityId)
            default:
                throw new IllegalArgumentException("Invalid entity class")
        }
    }

    @Override
    Set<Candidate> getAll() {
        return candidateDAO.getAll()
    }

    @Override
    int save(CandidateDTO candidateDTO) {
        ParamValidation.requireNonNull(candidateDTO, "CandidateDTO must not be null")

        return candidateDAO.save(candidateDTO)
    }

    @Override
    Set<Integer> saveAll(Set<CandidateDTO> candidateDTOs) {
        ParamValidation.requireNonNull(candidateDTOs, "candidateDTOs must not be null")

        Set<Integer> ids = new LinkedHashSet<>()
        for (CandidateDTO candidateDTO : candidateDTOs) {
            ids.add(Integer.valueOf(candidateDAO.save(candidateDTO)))
        }

        return ids
    }

    @Override
    Candidate updateById(int id, CandidateDTO candidateDTO) {
        ParamValidation.requirePositive(id, "id must be positive")
        ParamValidation.requireNonNull(candidateDTO, "CandidateDTO must not be null")

        return candidateDAO.update(id, candidateDTO)
    }

    @Override
    void deleteById(int id) {
        ParamValidation.requirePositive(id, "id must be positive")

        candidateDAO.delete(id)
    }
}

