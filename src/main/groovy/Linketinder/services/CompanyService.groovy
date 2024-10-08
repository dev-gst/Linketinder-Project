package Linketinder.services

import Linketinder.models.DTOs.AddressDTO
import Linketinder.models.DTOs.CompanyDTO
import Linketinder.models.entities.Address
import Linketinder.models.entities.Company
import Linketinder.models.entities.JobOpening
import Linketinder.repositories.AddressDAO
import Linketinder.repositories.CompanyDAO
import Linketinder.repositories.JobOpeningDAO
import Linketinder.repositories.SkillDAO

class CompanyService {
    CompanyDAO companyDAO
    JobOpeningDAO jobOpeningDAO
    AddressDAO addressDAO
    SkillDAO skillDAO


    CompanyService(
            CompanyDAO companyDAO,
            JobOpeningDAO jobOpeningDAO,
            AddressDAO addressDAO,
            SkillDAO skillDAO
    ) {
        this.companyDAO = companyDAO
        this.jobOpeningDAO = jobOpeningDAO
        this.addressDAO = addressDAO
        this.skillDAO = skillDAO
    }

    Company getById(int id) {
        Company company = companyDAO.getById(id)

        company.address = addressDAO.getByCompanyId(company.id)
        company.jobOpenings = jobOpeningDAO.getByCompanyId(company.id)

        return company
    }

    Company authenticate(String email, String password) {
        Company company = companyDAO.getByEmailAndPassword(email, password)

        if (company == null) {
            return null
        }

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
            int companyId,
            CompanyDTO companyDTO,
            AddressDTO addressDTO
    ) {
        Company oldCompany = companyDAO.getById(companyId)
        if (!oldCompany) {
            throw new IllegalArgumentException("Company not found for the given id")
        }

        int oldAddressID = addressDAO.getByCompanyId(companyId).id

        int addressID = addressDAO.save(addressDTO)
        companyDAO.update(companyId, companyDTO, addressID)

        addressDAO.delete(oldAddressID)
    }

    void delete(int id) {
        Company oldCompany = companyDAO.getById(id)
        if (!oldCompany) {
            throw new IllegalArgumentException("Company not found for the given id")
        }
        Address oldAddress = addressDAO.getByCompanyId(id)
        int oldAddressID = oldAddress != null ? oldAddress.id : 0

        cleanCompanyJobOpenings(id)

        companyDAO.delete(id)
        if (oldAddressID > 0){
            addressDAO.delete(oldAddressID)
        }
    }

    private void cleanCompanyJobOpenings(int companyId) {
        List<JobOpening> jobOpenings = jobOpeningDAO.getByCompanyId(companyId)
        if (jobOpenings.isEmpty()) {
            throw new IllegalArgumentException("Job openings not found for the given company ID")
        }

        for (JobOpening jobOpening : jobOpenings) {
            Address address = addressDAO.getByJobOpeningId(jobOpening.id)
            int addressID = address != null ? address.id : 0

            skillDAO.deleteJobOpeningSkills(jobOpening.id)
            jobOpeningDAO.delete(jobOpening.id)

            if (addressID > 0) {
                addressDAO.delete(addressID)
            }
        }
    }

}