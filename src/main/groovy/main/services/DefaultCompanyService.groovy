package main.services

import main.models.dtos.request.company.CompanyDTO
import main.models.entities.address.Address
import main.models.entities.company.Company
import main.models.entities.jobOpening.JobOpening
import main.repositories.CompanyDAO
import main.services.interfaces.AddressService
import main.services.interfaces.CompanyService
import main.util.exception.ParamValidation
import main.util.exception.custom.EntityNotFoundException
import main.util.exception.custom.NullCollectionException

class DefaultCompanyService implements CompanyService {

    private static final String EMAIL_FIELD = "email"

    CompanyDAO companyDAO

    AddressService addressService

    DefaultCompanyService(CompanyDAO companyDAO, AddressService addressService) {
        ParamValidation.requireNonNull(companyDAO, "companyDAO cannot be null")
        ParamValidation.requireNonNull(addressService, "addressService cannot be null")

        this.companyDAO = companyDAO
        this.addressService = addressService
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

        Set<Company> foundCompanies = getByField(EMAIL_FIELD, companyDTO.loginDetails.email)
        if (foundCompanies == null) throw new NullCollectionException("foundCompanies cannot be null")

        Company foundCompany = foundCompanies.isEmpty() ? null : foundCompanies[0]
        if (foundCompany != null) {
            return foundCompany.id
        }

        int addressId = addressService.save(companyDTO.companyDetailsDTO.addressDTO)

        return companyDAO.save(companyDTO, addressId)
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

        Set<Address> foundAddresses = addressService.getByEntityId(id, Company.class)
        if (foundAddresses == null) throw new NullCollectionException("foundAddresses cannot be null")

        Address address = foundAddresses.isEmpty() ? null : foundAddresses[0]
        if (address == null) {

            return updateWhenAddressIsNull(id, companyDTO)
        }

        return updateWhenAddressIsNotNull(id, companyDTO, address)
    }

    private Company updateWhenAddressIsNull(int id, CompanyDTO companyDTO) {
        int addressId = addressService.save(companyDTO.companyDetailsDTO.addressDTO)

        return companyDAO.updateById(id, companyDTO, addressId)
    }

    private Company updateWhenAddressIsNotNull(int id, CompanyDTO companyDTO, Address address) {
        addressService.updateById(address.id, companyDTO.companyDetailsDTO.addressDTO)

        return companyDAO.updateById(id, companyDTO, address.id)
    }

    @Override
    void deleteById(int id) {
        ParamValidation.requirePositive(id, "id cannot be negative")

        companyDAO.deleteById(id)
    }
}