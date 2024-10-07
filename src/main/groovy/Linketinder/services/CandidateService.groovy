
package Linketinder.services

import Linketinder.models.DTOs.AddressDTO
import Linketinder.models.DTOs.CandidateDTO
import Linketinder.models.DTOs.SkillDTO
import Linketinder.models.entities.Candidate
import Linketinder.models.entities.Skill
import Linketinder.repositories.AddressDAO
import Linketinder.repositories.CandidateDAO

import Linketinder.repositories.SkillDAO

class CandidateService {

    AddressDAO addressDAO
    CandidateDAO candidateDAO
    SkillDAO skillDAO

    SkillService skillService

    // TODO: Decouple skillDAO from CandidateService
    // TODO: Decouple addressDAO from CandidateService

    CandidateService(
            CandidateDAO candidateDAO,
            AddressDAO addressDAO,
            SkillDAO skillDAO,
            SkillService skillService
    ) {
        this.addressDAO = addressDAO
        this.candidateDAO = candidateDAO
        this.skillDAO = skillDAO
        this.skillService = skillService
    }

    Candidate getById(int id) {
        Candidate candidate = candidateDAO.getById(id)

        candidate.address = addressDAO.getByCandidateId(candidate.id)
        candidate.skills =  skillDAO.getByCandidateId(candidate.id)

        return candidate
    }

    List<Candidate> getAll() {
        List<Candidate> candidates = candidateDAO.getAll()

        for (Candidate candidate : candidates) {
            candidate.address = addressDAO.getByCandidateId(candidate.id)
            candidate.skills = skillDAO.getByCandidateId(candidate.id)
        }

        return candidates
    }

    void save(CandidateDTO candidateDTO, AddressDTO addressDTO, Set<SkillDTO> skillDTOList) {
        int addressID = addressDAO.save(addressDTO)
        Set<Skill> skills = skillService.save(skillDTOList)
        int candidateID = candidateDAO.save(candidateDTO, addressID)

        skillDAO.saveCandidateSkills(candidateID, skills)
    }

    void update(
            int candidateID,
            CandidateDTO candidateDTO,
            AddressDTO addressDTO,
            Set<SkillDTO> skillDTOList
    ) {
        int oldAddressID = candidateDAO.getById(candidateID).address.id
        AddressDAO.delete(oldAddressID)

        int newAddressID = addressDAO.save(addressDTO)
        candidateDAO.update(candidateID, candidateDTO, newAddressID)

        Set<Skill> skills = skillService.save(skillDTOList)
        skillDAO.saveCandidateSkills(candidateID, skills)
    }

    void delete(int id) {
        int addressID = candidateDAO.getById(id).address.id

        candidateDAO.delete(id)
        addressDAO.delete(addressID)

        skillDAO.deleteCandidateSkills(id)
    }
}

