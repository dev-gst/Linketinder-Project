package main.services

import main.models.dtos.skills.SkillDTO
import main.models.entities.Skill
import main.repositories.SkillDAO
import main.services.interfaces.SearchableService
import main.util.exception.ParamValidation

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
        ParamValidation.requireNonNull(fieldName, "Field name cannot be null")
        ParamValidation.requireNonNull(fieldValue, "Field value cannot be null")
        ParamValidation.requireNonBlank(fieldName, "Field name cannot be blank")
        ParamValidation.requireNonBlank(fieldValue, "Field value cannot be blank")

        return skillDAO.findByField(fieldName, fieldValue)
    }

    @Override
    Skill save(SkillDTO skillDTO) {
        ParamValidation.requireNonNull(skillDTO, "SkillDTO cannot be null")

        Skill skill = findByField("name", skillDTO.getName())[0]
        if (skill != null) {
            return skill
        } else {
            int newSkillId = skillDAO.save(skillDTO)
            return new Skill(newSkillId, skillDTO.getName())
        }
    }

    @Override
    Set<Skill> saveAll(Set<SkillDTO> skillDTOSet) {
        ParamValidation.requireNonNull(skillDTOSet, "SkillDTO set cannot be null")
        if (skillDTOSet.isEmpty()) throw new IllegalArgumentException("SkillDTO set cannot be empty")

        Set<Skill> skills = new LinkedHashSet<>()
        for (SkillDTO skillDTO : skillDTOSet) {
            skills.add(save(skillDTO))
        }

        return skills
    }

    @Override
    Skill updateById(int id, SkillDTO skillDTO) {
        ParamValidation.requireNonNull(skillDTO, "SkillDTO cannot be null")
        if (id <= 0) throw new IllegalArgumentException("Skill ID must be greater than 0")

        Skill skill = getById(id)
        if (skill == null) throw new IllegalArgumentException("Skill with ID $id not found")

        skill.setName(skillDTO.getName())
        skillDAO.updateById(id, skillDTO)

        return skill
    }

    @Override
    void deleteById(int id) {
        if (id <= 0) throw new IllegalArgumentException("Skill ID must be greater than 0")

        skillDAO.deleteById(id)
    }
}
