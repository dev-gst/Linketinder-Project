package application.services.interfaces

import application.models.dtos.request.SkillDTO
import application.models.entities.Skill

interface SkillService extends SearchableService<Skill, SkillDTO> {

    int saveCandidateSkill(int candidateId, int skillId)

    int saveJobOpeningSkill(int jobOpeningId, int skillId)

    void deleteCandidateSkill(int candidateId, int skillId)

    void deleteAllCandidateSkills(int candidateId)

    void deleteJobOpeningSkill(int jobOpeningId, int skillId)

    void deleteAllJobOpeningSkills(int jobOpeningId)
}