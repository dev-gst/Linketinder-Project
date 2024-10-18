package main.services

import main.models.DTOs.AddressDTO
import main.models.DTOs.JobOpeningDTO
import main.models.DTOs.SkillDTO
import main.models.entities.Address
import main.models.entities.JobOpening
import main.models.entities.Skill
import main.repositories.AddressDAO
import main.repositories.JobOpeningDAO
import main.repositories.SkillDAO

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
            SkillService skillService,
            CompanyService companyService,
            AddressDAO addressDAO
    ) {
        this.jobOpeningDAO = jobOpeningDAO
        this.skillDAO = skillDAO
        this.skillService = skillService
        this.companyService = companyService
        this.addressDAO = addressDAO
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
            if (jobOpening == null) continue

            Address address = addressDAO.getByJobOpeningId(jobOpening.id)

            jobOpening.company = companyService.getByJobOpeningId(jobOpening.id)

            jobOpening.requiredSkills = skillDAO.getByJobOpeningId(jobOpening.id)

            jobOpening.address = address ? address : null
        }

        return jobOpenings
    }

    void save(
            JobOpeningDTO jobOpeningDTO,
            int companyId,
            AddressDTO addressDTO,
            Set<SkillDTO> skillDTOs
    ) {
        if (jobOpeningDTO.isRemote) {
            addressDTO = null
        }

        int addressId = 0
        if (addressDTO != null) {
            addressId = addressDAO.save(addressDTO)
        }

        int jobOpeningId = jobOpeningDAO.save(jobOpeningDTO, companyId, addressId)
        Set<Skill> skills = skillService.save(skillDTOs)

        skillDAO.saveJobOpeningSkills(jobOpeningId, skills)
    }

    void update(
            int jobOpeningId,
            JobOpeningDTO jobOpeningDTO,
            int companyId,
            AddressDTO addressDTO,
            Set<SkillDTO> skillDTOs
    ) {
        JobOpening oldJobOpening = jobOpeningDAO.getById(jobOpeningId)
        if (!oldJobOpening) {
            throw new IllegalArgumentException("Job opening not found for the given ID")
        }

        int addressId
        int oldAddressID
        if (jobOpeningDTO.isRemote) {
            addressId = 0
            oldAddressID = 0
        } else {
            Address oldAddress = addressDAO.getByJobOpeningId(jobOpeningId)

            oldAddressID = oldAddress ? oldAddress.id : 0
            addressId = addressDAO.save(addressDTO)
        }

        jobOpeningDAO.update(jobOpeningId, jobOpeningDTO, companyId, addressId)
        Set<Skill> skills = skillService.save(skillDTOs)

        skillDAO.saveJobOpeningSkills(jobOpeningId, skills)

        if (oldAddressID > 0) {
            addressDAO.delete(oldAddressID)
        }
    }

    void delete(int id) {
        JobOpening oldJobOpening = jobOpeningDAO.getById(id)
        if (!oldJobOpening) {
            throw new IllegalArgumentException("Job opening not found for the given ID")
        }

        int oldAddressID = oldJobOpening.address.id

        skillDAO.deleteJobOpeningSkills(id)
        jobOpeningDAO.delete(id)
        addressDAO.delete(oldAddressID)
    }
}
