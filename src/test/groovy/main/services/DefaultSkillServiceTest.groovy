package main.services

import main.models.dtos.anonresponse.AnonSkillDTO
import main.models.dtos.request.SkillDTO
import main.models.entities.Candidate
import main.models.entities.Skill
import main.models.entities.jobOpening.JobOpening
import main.repositories.interfaces.SkillDAO
import main.util.exception.custom.ClassNotFoundException
import mocks.SkillMock
import spock.lang.Specification


class DefaultSkillServiceTest extends Specification {

    Skill skill1
    Skill skill2
    SkillDTO skillDTO1
    SkillDTO skillDTO2
    SkillDAO skillDAO
    DefaultSkillService skillService

    def setup() {
        SkillMock skillMock = new SkillMock()
        skill1 = skillMock.createSkillMock(1)
        skill2 = skillMock.createSkillMock(2)
        skillDTO1 = skillMock.createSkillDTOMock(1)
        skillDTO2 = skillMock.createSkillDTOMock(2)

        skillDAO = Mock(SkillDAO)
        skillService = new DefaultSkillService(skillDAO)
    }

    def "skill service constructor should throw exception when skillDAO is null"() {
        when:
        new DefaultSkillService(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "get by id should return skill when found"() {
        skillDAO.getById(1) >> skill1

        when:
        Skill foundSkill = skillService.getById(1)

        then:
        foundSkill.id == 1
        foundSkill.name == "Skill 1"
        1 * skillDAO.getById(1) >> skill1
    }

    def "get by id should throw exception when skill id is less than 1"() {
        when:
        skillService.getById(0)

        then:
        thrown(IllegalArgumentException)
    }

    def "get anon by id should return anon skill when found"() {
        skillDAO.getById(1) >> skill1

        when:
        AnonSkillDTO anonSkillDTO = skillService.getAnonById(1)

        then:
        anonSkillDTO.name == "Skill 1"
        1 * skillDAO.getById(1) >> skill1
    }

    def "get anon by id should throw exception when skill id is less than 1"() {
        when:
        skillService.getAnonById(0)

        then:
        thrown(IllegalArgumentException)
    }

    def "get by entity id should return skills for candidate"() {
        given:
        Set<Skill> skills = new LinkedHashSet<>()
        skills.add(skill1)
        skillDAO.getByCandidateId(1) >> skills

        when:
        Set<Skill> foundSkills = skillService.getByEntityId(1, Candidate.class)

        then:
        foundSkills.size() == 1
        foundSkills[0].id == 1
        foundSkills[0].name == "Skill 1"
    }

    def "get by entity id should return skills for job opening"() {
        given:
        Set<Skill> skills = new LinkedHashSet<>()
        skills.add(skill1)
        skillDAO.getByJobOpeningId(1) >> skills

        when:
        Set<Skill> foundSkills = skillService.getByEntityId(1, JobOpening.class)

        then:
        foundSkills.size() == 1
        foundSkills[0].id == 1
        foundSkills[0].name == "Skill 1"
    }

    def "get by entity id should throw exception when entity id is less than 1"() {
        when:
        skillService.getByEntityId(0, Candidate.class)

        then:
        thrown(IllegalArgumentException)
    }

    def "get by entity id should throw exception when entity class is null"() {
        when:
        skillService.getByEntityId(1, null)

        then:
        thrown(IllegalArgumentException)
    }

    def "get by entity id should throw exception when entity class is not found"() {
        when:
        skillService.getByEntityId(1, String.class)

        then:
        thrown(ClassNotFoundException)
    }

    def "get by field should return skill when found"() {
        Set<Skill> skills = new LinkedHashSet<>()
        skills.add(skill1)
        skillDAO.getByField("name", "Skill 1") >> skills

        when:
        Set<Skill> foundSkills = skillService.getByField("name", "Skill 1")

        then:
        foundSkills[0].id == 1
        foundSkills[0].name == "Skill 1"
    }

    def "get by field should throw exception when skill name is null"() {
        when:
        skillService.getByField("name", null)

        then:
        thrown(IllegalArgumentException)
    }

    def "get by field should throw exception when skill name is blank"() {
        when:
        skillService.getByField("name", "")

        then:
        thrown(IllegalArgumentException)
    }

    def "get all should return all skills"() {
        Set<Skill> skills = new LinkedHashSet<>()
        skills.add(skill1)
        skills.add(skill2)
        skillDAO.getAll() >> skills

        when:
        Set<Skill> foundSkills = skillService.getAll()

        then:
        foundSkills.size() == 2
        foundSkills[0].id == 1
        foundSkills[0].name == "Skill 1"
        foundSkills[1].id == 2
        foundSkills[1].name == "Skill 2"
    }

    def "save should return newly saved skill"() {
        given:
        skillDAO.getByField("name", "Skill 1") >> new LinkedHashSet<>()
        skillDAO.save(skillDTO1) >> Integer.valueOf(67)

        when:
        Integer id = skillService.save(skillDTO1)

        then:
        id == Integer.valueOf(67)
    }

    def "save should return already saved skill"() {
        given:
        Set<Skill> foundSkills = new LinkedHashSet<>()
        foundSkills.add(skill1)
        skillDAO.getByField("name", "Skill 1") >> foundSkills

        when:
        Integer id = skillService.save(skillDTO1)

        then:
        id == Integer.valueOf(1)
    }

    def "save should throw exception when skillDTO is null"() {
        when:
        skillService.save(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "save all should return already saved and newly saved skills"() {
        given:
        Set<SkillDTO> skillDTOSet = new LinkedHashSet<>()
        skillDTOSet.add(skillDTO1)
        skillDTOSet.add(skillDTO2)

        Set<Skill> existingSkills = new LinkedHashSet<>()
        existingSkills.add(skill2)

        skillDAO.getByField("name", "Skill 1") >> new LinkedHashSet<Skill>()
        skillDAO.save(skillDTO1) >> Integer.valueOf(1)

        skillDAO.getByField("name", "Skill 2") >> existingSkills

        when:
        Set<Integer> savedIds = skillService.saveAll(skillDTOSet)

        then:
        savedIds[0] == 1
        savedIds[1] == 2
    }

    def "save all should throw exception when skillDTO set is null"() {
        when:
        skillService.saveAll(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "save all should throw exception when skillDTO set is empty"() {
        when:
        skillService.saveAll(new LinkedHashSet<>())

        then:
        thrown(IllegalArgumentException)
    }

    def "update by id should return updated skill"() {
        given:
        skillDAO.getById(1) >> skill1
        skillDAO.update(1, skillDTO1) >> skill1

        when:
        Skill updatedSkill = skillService.updateById(1, skillDTO1)

        then:
        updatedSkill.id == 1
        updatedSkill.name == "Skill 1"
    }

    def "update by id should throw exception when skill id is less than 1"() {
        when:
        skillService.updateById(0, skillDTO1)

        then:
        thrown(IllegalArgumentException)
    }

    def "update by id should throw exception when skillDTO is null"() {
        when:
        skillService.updateById(1, null)

        then:
        thrown(IllegalArgumentException)
    }

    def "delete by id should return deleted skill"() {
        when:
        skillDAO.delete(1)

        then:
        1 * skillDAO.delete(1)
    }

    def "delete by id should throw exception when skill id is less than 1"() {
        when:
        skillService.deleteById(0)

        then:
        thrown(IllegalArgumentException)
    }
}
