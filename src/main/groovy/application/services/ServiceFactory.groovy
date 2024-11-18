package application.services

import application.repositories.DAOFactory
import application.services.interfaces.*

class ServiceFactory {

    static CandidateService createCandidateService() {
        return new DefaultCandidateService(DAOFactory.createCandidateDAO())
    }

    static AddressService createAddressService() {
        return new DefaultAddressService(DAOFactory.createAddressDAO())
    }

    static CompanyService createCompanyService() {
        return new DefaultCompanyService(DAOFactory.createCompanyDAO())
    }

    static JobOpeningService createJobOpeningService() {
        return new DefaultJobOpeningService(DAOFactory.createJobOpeningDAO())
    }

    static SkillService createSkillService() {
        return new DefaultSkillService(DAOFactory.createSkillDAO())
    }
}
