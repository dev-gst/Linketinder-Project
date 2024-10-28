package mocks

import main.models.dtos.request.SkillDTO
import main.models.entities.Skill
import spock.lang.Specification

class SkillMock extends Specification {

    Skill createSkillMock(int n) {
        Skill skill = Mock(Skill)

        skill.id >> n
        skill.name >> "Skill $n"
    }

    SkillDTO createSkillDTOMock() {
        SkillDTO skillDTO = Mock(SkillDTO)

        skillDTO.getName() >> "Skill"
    }
}
