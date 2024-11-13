package mocks

import application.models.dtos.request.SkillDTO
import application.models.entities.Skill
import spock.lang.Specification

class SkillMock extends Specification {

    Skill createSkillMock(int n) {
        Skill skill = Mock(Skill)

        skill.id >> n
        skill.name >> "Skill $n"
        skill.toString() >> "Skill $n"

        return skill
    }

    SkillDTO createSkillDTOMock(int n) {
        SkillDTO skillDTO = Mock(SkillDTO)

        skillDTO.getName() >> "Skill $n"
        skillDTO.toString() >> "Skill $n"

        return skillDTO
    }
}
