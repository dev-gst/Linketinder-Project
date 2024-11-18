package application.services

import application.models.dtos.anonresponse.AnonSkillDTO
import application.models.dtos.request.SkillDTO
import application.models.entities.Candidate
import application.models.entities.JobOpening
import application.models.entities.Skill
import application.repositories.interfaces.SkillDAO
import application.services.interfaces.AnonService
import application.services.interfaces.SkillService
import application.utils.exceptions.ClassNotFoundException
import application.utils.exceptions.EntityNotFoundException
import application.utils.exceptions.NullCollectionException
import application.utils.validation.ParamValidation

class DefaultSkillService implements SkillService, AnonService<AnonSkillDTO> {

    SkillDAO skillDAO

    DefaultSkillService(SkillDAO skillDAO) {
        ParamValidation.requireNonNull(skillDAO, "SkillDAO cannot be null")

        this.skillDAO = skillDAO
    }

    @Override
    Skill getById(int id) {
        ParamValidation.requirePositive(id, "Skill ID must be greater than 0")

        return skillDAO.getById(id)
    }

    @Override
    Set<Skill> getByEntityId(int entityId, Class<?> entityClazz) {
        ParamValidation.requirePositive(entityId, "Entity ID must be greater than 0")
        ParamValidation.requireNonNull(entityClazz, "Entity class cannot be null")

        switch (entityClazz) {
            case Candidate.class:
                return skillDAO.getByCandidateId(entityId)
            case JobOpening.class:
                return skillDAO.getByJobOpeningId(entityId)
            default:
                throw new ClassNotFoundException("Class not found for this context")
        }
    }

    @Override
    AnonSkillDTO getAnonById(int id) {
        ParamValidation.requirePositive(id, "Skill ID must be greater than 0")

        Skill skill = getById(id)
        AnonSkillDTO anonSkillDTO = new AnonSkillDTO(skill.name)

        return anonSkillDTO
    }

    @Override
    Set<Skill> getAll() {
        return skillDAO.getAll()
    }

    @Override
    Set<AnonSkillDTO> getAllAnon() {
        Set<Skill> skills = getAll()
        Set<AnonSkillDTO> anonSkillDTOs = new LinkedHashSet<>()
        for (Skill skill : skills) {
            anonSkillDTOs.add(new AnonSkillDTO(skill.name))
        }

        return anonSkillDTOs
    }

    @Override
    Set<Skill> getByField(String fieldName, String fieldValue) {
        ParamValidation.requireNonBlank(fieldName, "Field name cannot be blank or null")
        ParamValidation.requireNonBlank(fieldValue, "Field value cannot be blank or null")

        return skillDAO.getByField(fieldName, fieldValue)
    }

    @Override
    int save(SkillDTO skillDTO) {
        ParamValidation.requireNonNull(skillDTO, "SkillDTO cannot be null")

        Set<Skill> foundSkills = getByField("name", skillDTO.getName())
        if (foundSkills == null) throw new NullCollectionException("Found skills cannot be null")

        Skill skill = foundSkills.isEmpty() ? null : foundSkills[0]
        if (skill != null) {
            return skill.id
        }

        return skillDAO.save(skillDTO)
    }

    @Override
    Set<Integer> saveAll(Set<SkillDTO> skillDTOSet) {
        ParamValidation.requireNonEmpty(skillDTOSet, "SkillDTO set cannot be null or empty")

        Set<Integer> skills = new LinkedHashSet<>()
        for (SkillDTO skillDTO : skillDTOSet) {
            skills.add(Integer.valueOf(save(skillDTO)))
        }

        return skills
    }

    @Override
    Skill updateById(int id, SkillDTO skillDTO) {
        ParamValidation.requireNonNull(skillDTO, "SkillDTO cannot be null")
        ParamValidation.requirePositive(id, "Skill ID must be greater than 0")

        Skill skill = skillDAO.update(id, skillDTO)
        if (skill == null) throw new EntityNotFoundException("Skill with ID $id not found")

        return skill
    }

    @Override
    void deleteById(int id) {
        ParamValidation.requirePositive(id, "Skill ID must be greater than 0")

        skillDAO.delete(id)
    }

    @Override
    int saveCandidateSkill(int candidateId, int skillId) {
        ParamValidation.requirePositive(candidateId, "Candidate ID must be greater than 0")
        ParamValidation.requirePositive(skillId, "Skill ID must be greater than 0")

        return skillDAO.saveCandidateSkill(candidateId, skillId)
    }

    @Override
    int saveJobOpeningSkill(int jobOpeningId, int skillId) {
        ParamValidation.requirePositive(jobOpeningId, "Job Opening ID must be greater than 0")
        ParamValidation.requirePositive(skillId, "Skill ID must be greater than 0")

        return skillDAO.saveJobOpeningSkill(jobOpeningId, skillId)
    }

    @Override
    void deleteCandidateSkill(int candidateId, int skillId) {
        ParamValidation.requirePositive(candidateId, "Candidate ID must be greater than 0")
        ParamValidation.requirePositive(skillId, "Skill ID must be greater than 0")

        skillDAO.deleteCandidateSkill(candidateId, skillId)
    }

    @Override
    void deleteAllCandidateSkills(int candidateId) {
        ParamValidation.requirePositive(candidateId, "Candidate ID must be greater than 0")

        skillDAO.deleteAllCandidateSkills(candidateId)
    }

    @Override
    void deleteJobOpeningSkill(int jobOpeningId, int skillId) {
        ParamValidation.requirePositive(jobOpeningId, "Job Opening ID must be greater than 0")
        ParamValidation.requirePositive(skillId, "Skill ID must be greater than 0")

        skillDAO.deleteJobOpeningSkill(jobOpeningId, skillId)
    }

    @Override
    void deleteAllJobOpeningSkills(int jobOpeningId) {
        ParamValidation.requirePositive(jobOpeningId, "Job Opening ID must be greater than 0")

        skillDAO.deleteAllJobOpeningSkills(jobOpeningId)
    }
}
