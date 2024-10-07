package Linketinder.services

import Linketinder.models.DTOs.SkillDTO
import Linketinder.models.entities.Skill
import Linketinder.repositories.SkillDAO

class SkillService {

    SkillDAO skillDAO

    SkillService(SkillDAO skillDAO) {
        this.skillDAO = skillDAO
    }

    Set<Skill> save(Set<SkillDTO> skillDTOList) {
        Set<Skill> skills = new HashSet<>()
        List<Skill> dbSkills = skillDAO.getAll()

        for (SkillDTO skillDTO : skillDTOList) {
            Skill dbSkill = dbSkills.stream()
                    .filter(s -> s.name == skillDTO.name)
                    .findFirst()
                    .orElse(null)

            if (dbSkill) {
                skills.add(dbSkill)
            } else {
                int newSkillInDbId = skillDAO.save(skillDTO)
                Skill newSkillInDb = skillDAO.getById(newSkillInDbId)

                skills.add(newSkillInDb)
            }
        }

        return skills
    }
}
