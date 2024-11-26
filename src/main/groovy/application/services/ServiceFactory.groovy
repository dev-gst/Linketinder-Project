package application.services

import application.repositories.PostgresDAOFactory

class ServiceFactory {

    static CandidateService createCandidateService() {
        return new CandidateService(PostgresDAOFactory.createCandidateDAO())
    }

    static AddressService createAddressService() {
        return new AddressService(PostgresDAOFactory.createAddressDAO())
    }

    static CompanyService createCompanyService() {
        return new CompanyService(PostgresDAOFactory.createCompanyDAO())
    }

    static JobOpeningService createJobOpeningService() {
        return new JobOpeningService(PostgresDAOFactory.createJobOpeningDAO())
    }

    static SkillService createSkillService() {
        return new SkillService(PostgresDAOFactory.createSkillDAO())
    }
}
