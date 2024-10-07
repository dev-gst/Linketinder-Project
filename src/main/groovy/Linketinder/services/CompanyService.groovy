package Linketinder.services

import Linketinder.models.DTOs.AddressDTO
import Linketinder.models.DTOs.CompanyDTO
import Linketinder.models.entities.Company
import Linketinder.repositories.AddressDAO
import Linketinder.repositories.CompanyDAO
import Linketinder.repositories.JobOpeningDAO

class CompanyService {
    CompanyDAO companyDAO
    JobOpeningDAO jobOpeningDAO
    AddressDAO addressDAO

    JobOpeningService jobOpeningService

    CompanyService(
            CompanyDAO companyDAO,
            JobOpeningDAO jobOpeningDAO,
            AddressDAO addressDAO,
            JobOpeningService jobOpeningService
    ) {
        this.companyDAO = companyDAO
        this.jobOpeningDAO = jobOpeningDAO
        this.addressDAO = addressDAO
        this.jobOpeningService = jobOpeningService
    }

    Company getById(int id) {
        Company company = companyDAO.getById(id)

        company.address = addressDAO.getByCompanyId(company.id)
        company.jobOpenings = jobOpeningDAO.getByCompanyId(company.id)

        return company
    }

    Company getByJobOpeningId(int jobOpeningId) {
        Company company = companyDAO.getByJobOpeningId(jobOpeningId)

        company.address = addressDAO.getByCompanyId(company.id)
        company.jobOpenings = jobOpeningDAO.getByCompanyId(company.id)

        return company
    }

    List<Company> getAll() {
        List<Company> companies = companyDAO.getAll()

        companies.each { company ->
            company.address = addressDAO.getByCompanyId(company.id)
            company.jobOpenings = jobOpeningDAO.getByCompanyId(company.id)
        }

        return companies
    }

    void save(
            CompanyDTO companyDTO,
            AddressDTO addressDTO
    ) {
        int addressID = addressDAO.save(addressDTO)

        companyDAO.save(companyDTO, addressID)
    }

    void update(
            int companyID,
            CompanyDTO companyDTO,
            AddressDTO addressDTO
    ) {
        Company oldCompany = companyDAO.getById(companyID)
        if (!oldCompany) {
            throw new IllegalArgumentException("Company not found for the given id")
        }

        int oldAddressID = oldCompany.address.id

        int addressID = addressDAO.save(addressDTO)
        companyDAO.update(companyID, companyDTO, addressID)

        addressDAO.delete(oldAddressID)
    }

    void delete(int id) {
        Company oldCompany = companyDAO.getById(id)
        if (!oldCompany) {
            throw new IllegalArgumentException("Company not found for the given id")
        }

        int oldAddressID = companyDAO.getById(id).address.id

        jobOpeningService.deleteByCompanyId(id)

        companyDAO.delete(id)
        addressDAO.delete(oldAddressID)
    }

}