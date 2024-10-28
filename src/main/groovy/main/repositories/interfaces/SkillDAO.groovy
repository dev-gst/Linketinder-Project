package main.repositories.interfaces

import main.models.dtos.request.SkillDTO
import main.models.entities.Skill

interface SkillDAO extends DAO<Skill, SkillDTO> {

    Set<Skill> getByCandidateId(int candidateId)

    Set<Skill> getByJobOpeningId(int jobOpeningId)
}