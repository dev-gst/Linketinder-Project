package Linketinder.services

import Linketinder.models.DTOs.JobOpeningDTO
import Linketinder.models.DTOs.SkillDTO
import Linketinder.models.entities.JobOpening
import Linketinder.models.entities.Skill
import Linketinder.repositories.AddressDAO
import Linketinder.repositories.JobOpeningDAO
import Linketinder.repositories.SkillDAO

class JobOpeningService {
    JobOpeningDAO jobOpeningDAO
    SkillDAO skillDAO
    AddressDAO addressDAO

    CompanyService companyService
    SkillService skillService

    // TODO: Decouple DAOs from JobOpeningService

    JobOpeningService(
            JobOpeningDAO jobOpeningDAO,
            SkillDAO skillDAO,
            CompanyService companyService
    ) {
        this.jobOpeningDAO = jobOpeningDAO
        this.skillDAO = skillDAO
        this.companyService = companyService
    }

    JobOpening getById(int id) {
        JobOpening jobOpening = jobOpeningDAO.getById(id)

        jobOpening.company = companyService.getByJobOpeningId(id)
        jobOpening.address = addressDAO.getByJobOpeningId(id)
        jobOpening.requiredSkills = skillDAO.getByJobOpeningId(jobOpening.id)

        return jobOpening
    }

    List<JobOpening> getByCompanyId(int companyId) {
        List<JobOpening> jobOpenings = jobOpeningDAO.getByCompanyId(companyId)

        for (JobOpening jobOpening : jobOpenings) {
            jobOpening.company = companyService.getByJobOpeningId(jobOpening.id)
            jobOpening.address = addressDAO.getByJobOpeningId(jobOpening.id)
            jobOpening.requiredSkills = skillDAO.getByJobOpeningId(jobOpening.id)
        }

        return jobOpenings
    }

    List<JobOpening> getAll() {
        List<JobOpening> jobOpenings = jobOpeningDAO.getAll()

        for (JobOpening jobOpening : jobOpenings) {
            jobOpening.company = companyService.getByJobOpeningId(jobOpening.id)
            jobOpening.address = addressDAO.getByJobOpeningId(jobOpening.id)
            jobOpening.requiredSkills = skillDAO.getByJobOpeningId(jobOpening.id)
        }

        return jobOpenings
    }

    void save(
            JobOpeningDTO jobOpeningDTO,
            int companyId,
            int addressId,
            Set<SkillDTO> skillDTOs
    ) {
        int jobOpeningId = jobOpeningDAO.save(jobOpeningDTO, companyId, addressId)
        Set<Skill> skills = skillService.save(skillDTOs)

        skillDAO.saveJobOpeningSkills(jobOpeningId, skills)
    }

    void update(
            int jobOpeningId,
            JobOpeningDTO jobOpeningDTO,
            int companyId,
            int addressId,
            Set<SkillDTO> skillDTOs
    ) {
        JobOpening oldJobOpening = jobOpeningDAO.getById(jobOpeningId)
        if (!oldJobOpening) {
            throw new IllegalArgumentException("Job opening not found for the given ID")
        }

        int oldAddressID = oldJobOpening.address.id

        jobOpeningDAO.update(jobOpeningId, jobOpeningDTO, companyId, addressId)
        Set<Skill> skills = skillService.save(skillDTOs)

        skillDAO.saveJobOpeningSkills(jobOpeningId, skills)

        addressDAO.delete(oldAddressID)
    }

    void delete(int id) {
        JobOpening oldJobOpening = jobOpeningDAO.getById(id)
        if (!oldJobOpening) {
            throw new IllegalArgumentException("Job opening not found for the given ID")
        }

        int oldAddressID = oldJobOpening.address.id

        jobOpeningDAO.delete(id)
        addressDAO.delete(oldAddressID)

        skillDAO.deleteJobOpeningSkills(id)
    }

    void deleteByCompanyId(int id) {
        List<JobOpening> jobOpenings = jobOpeningDAO.getByCompanyId(id)
        if (jobOpenings.isEmpty()) {
            throw new IllegalArgumentException("Job openings not found for the given company ID")
        }

        for (JobOpening jobOpening : jobOpenings) {
            int addressID = jobOpening.address.id

            jobOpeningDAO.delete(jobOpening.id)
            addressDAO.delete(addressID)
            skillDAO.deleteJobOpeningSkills(jobOpening.id)
        }
    }
}
