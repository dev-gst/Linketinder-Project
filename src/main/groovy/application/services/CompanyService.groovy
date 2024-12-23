package application.services

import application.models.dtos.request.CompanyDTO
import application.models.entities.Company
import application.models.entities.JobOpening
import application.repositories.interfaces.CompanyDAO
import application.services.interfaces.AuthService
import application.services.interfaces.Service
import application.utils.exceptions.EntityNotFoundException
import application.utils.exceptions.NullCollectionException
import application.utils.validation.ParamValidation

class CompanyService implements Service<Company, CompanyDTO>, AuthService<Company> {

    private static final String EMAIL_FIELD = "email"

    CompanyDAO companyDAO

    CompanyService(CompanyDAO companyDAO) {
        ParamValidation.requireNonNull(companyDAO, "companyDAO cannot be null")

        this.companyDAO = companyDAO
    }

    @Override
    Company getById(int id) {
        ParamValidation.requirePositive(id, "id cannot be negative")

        return companyDAO.getById(id)
    }

    @Override
    Set<Company> getByField(String fieldName, String fieldValue) {
        ParamValidation.requireNonBlank(fieldName, "fieldName cannot be null or blank")
        ParamValidation.requireNonBlank(fieldValue, "fieldValue cannot be null or blank")

        return companyDAO.getByField(fieldName, fieldValue)
    }

    @Override
    Set<Company> getByEntityId(int entityId, Class<?> entityClazz) {
        ParamValidation.requirePositive(entityId, "entityId cannot be negative")
        ParamValidation.requireNonNull(entityClazz, "entityClazz cannot be null")

        switch (entityClazz) {
            case JobOpening.class:
                return companyDAO.getByJobOpeningId(entityId)
            default:
                throw new ClassNotFoundException("Class not found for this context")
        }
    }

    @Override
    Set<Company> getAll() {
        return companyDAO.getAll()
    }

    @Override
    int save(CompanyDTO companyDTO) {
        ParamValidation.requireNonNull(companyDTO, "companyDTO cannot be null")

        Set<Company> foundCompanies = getByField(EMAIL_FIELD, companyDTO.loginDetailsDTO.email)
        if (foundCompanies == null) throw new NullCollectionException("foundCompanies cannot be null")

        Company foundCompany = foundCompanies.isEmpty() ? null : foundCompanies[0]
        if (foundCompany != null) {
            return foundCompany.id
        }

        return companyDAO.save(companyDTO)
    }

    @Override
    Set<Integer> saveAll(Set<CompanyDTO> companyDTOS) {
        ParamValidation.requireNonNull(companyDTOS, "companyDTOS cannot be null")

        Set<Integer> ids = new LinkedHashSet<>()
        for (CompanyDTO companyDTO : companyDTOS) {
            ids.add(Integer.valueOf(save(companyDTO)))
        }

        return ids
    }

    @Override
    Company updateById(int id, CompanyDTO companyDTO) {
        ParamValidation.requirePositive(id, "id cannot be negative")
        ParamValidation.requireNonNull(companyDTO, "companyDTO cannot be null")
        if (getById(id) == null) throw new EntityNotFoundException("Company with ID ${id} not found")


        return companyDAO.update(id, companyDTO)
    }

    @Override
    void deleteById(int id) {
        ParamValidation.requirePositive(id, "id cannot be negative")

        companyDAO.delete(id)
    }

    @Override
    Company authenticate(String email, String password) {
        ParamValidation.requireNonNull(email, "email must not be null")
        ParamValidation.requireNonNull(password, "password must not be null")

        return companyDAO.authenticate(email, password)
    }
}