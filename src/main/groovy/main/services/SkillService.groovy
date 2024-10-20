package main.services

import main.models.dtos.skills.SkillDTO
import main.models.entities.Skill
import main.repositories.SkillDAO

class SkillService {

    SkillDAO skillDAO

    SkillService(SkillDAO skillDAO) {
        Objects.requireNonNull(skillDAO, "SkillDAO cannot be null")

        this.skillDAO = skillDAO
    }

    Skill getSkillByName(String skillName) {
        Objects.requireNonNull(skillName, "Skill name cannot be null")
        if (skillName.isBlank()) throw new IllegalArgumentException("Skill name cannot be blank")

        return skillDAO.getByName(skillName)
    }

    Set<Skill> saveAll(Set<SkillDTO> skillDTOSet) {
        Objects.requireNonNull(skillDTOSet, "SkillDTO set cannot be null")

        if (skillDTOSet.isEmpty()) throw new IllegalArgumentException("SkillDTO set cannot be empty")

        Set<Skill> skills = new LinkedHashSet<>()
        for (SkillDTO skillDTO : skillDTOSet) {
            skills.add(save(skillDTO))
        }

        return skills
    }

    Skill save(SkillDTO skillDTO) {
        Objects.requireNonNull(skillDTO, "SkillDTO cannot be null")

        Skill skill = getSkillByName(skillDTO.getName())
        if (skill != null) {
            return skill
        } else {
            int newSkillId = skillDAO.save(skillDTO)

            return new Skill(newSkillId, skillDTO.getName())
        }
    }
}
