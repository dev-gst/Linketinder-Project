package main.services

import main.models.dtos.skills.SkillDTO
import main.models.entities.Skill
import main.repositories.SkillDAO
import main.services.interfaces.SearchableService
import main.util.exception.ParamValidation
import main.util.exception.custom.EntityNotFoundException

class SkillService implements SearchableService<Skill, SkillDTO> {

    SkillDAO skillDAO

    SkillService(SkillDAO skillDAO) {
        ParamValidation.requireNonNull(skillDAO, "SkillDAO cannot be null")

        this.skillDAO = skillDAO
    }

    @Override
    Skill getById(int id) {
        ParamValidation.requirePositive(id, "Skill ID must be greater than 0")

        return skillDAO.getById(id)
    }

    @Override
    Set<Skill> getAll() {
        return skillDAO.getAll()
    }

    @Override
    Set<Skill> findByField(String fieldName, String fieldValue) {
        ParamValidation.requireNonBlank(fieldName, "Field name cannot be blank or null")
        ParamValidation.requireNonBlank(fieldValue, "Field value cannot be blank or null")

        return skillDAO.findByField(fieldName, fieldValue)
    }

    @Override
    int save(SkillDTO skillDTO) {
        ParamValidation.requireNonNull(skillDTO, "SkillDTO cannot be null")

        Set<Skill> skills = findByField("name", skillDTO.getName())
        if (skills == null) throw new EntityNotFoundException("Skills for this parameters 'name', ${skillDTO.getName()} not found")

        Skill skill = skills.isEmpty() ? null : skills[0]
        if (skill != null) {
            return skill.id
        } else {
            return skillDAO.save(skillDTO)
        }
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

        Skill skill = skillDAO.updateById(id, skillDTO)
        if (skill == null) throw new EntityNotFoundException("Skill with ID $id not found")

        return skill
    }

    @Override
    void deleteById(int id) {
        ParamValidation.requirePositive(id, "Skill ID must be greater than 0")

        skillDAO.deleteById(id)
    }
}
