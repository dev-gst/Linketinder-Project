package main.services.interfaces

import main.models.dtos.request.SkillDTO
import main.models.entities.Skill

interface SkillService extends SearchableService<Skill, SkillDTO> {

    int saveCandidateSkill(int candidateId, int skillId)
}