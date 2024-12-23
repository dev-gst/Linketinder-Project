package application.repositories.interfaces

import application.models.dtos.request.SkillDTO
import application.models.entities.Skill

interface SkillDAO extends DAO<Skill, SkillDTO> {

    Set<Skill> getByCandidateId(int candidateId)

    Set<Skill> getByJobOpeningId(int jobOpeningId)

    int saveCandidateSkill(int candidateId, int skillId)

    int saveJobOpeningSkill(int jobOpeningId, int skillId)

    void deleteCandidateSkill(int candidateId, int skillId)

    void deleteAllCandidateSkills(int candidateId)

    void deleteJobOpeningSkill(int jobOpeningId, int skillId)

    void deleteAllJobOpeningSkills(int jobOpeningId)
}