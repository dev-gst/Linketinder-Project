package Linketinder.services

import Linketinder.models.entities.Company
import Linketinder.models.entities.JobOpening
import Linketinder.repositories.AddressDAO
import Linketinder.repositories.CompanyDAO
import Linketinder.repositories.JobOpeningDAO

class CompanyService {
    CompanyDAO companyDAO
    JobOpeningDAO jobOpeningDAO
    AddressDAO addressDAO

    CompanyService(
            CompanyDAO companyDAO,
            JobOpeningDAO jobOpeningDAO,
            AddressDAO addressDAO
    ) {
        this.companyDAO = companyDAO
        this.jobOpeningDAO = jobOpeningDAO
        this.addressDAO = addressDAO
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
}