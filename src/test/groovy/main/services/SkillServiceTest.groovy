package main.services

import main.models.dtos.skills.SkillDTO
import main.models.entities.Skill
import main.repositories.SkillDAO
import spock.lang.Specification

class SkillServiceTest extends Specification {

    SkillDAO skillDAO
    SkillService skillService

    def setup() {
        skillDAO = Mock(SkillDAO)
        skillService = new SkillService(skillDAO)
    }

    def "skill service constructor should throw exception when skillDAO is null"() {
        when:
        new SkillService(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "find by field should return skill when found"() {
        Set<Skill> skills = new LinkedHashSet<>()
        skills.add(new Skill(1, "Java"))

        when:
        skillDAO.findByField("name", "Java") >> skills
        Set<Skill> foundSkills = skillService.findByField("name", "Java")

        then:
        foundSkills[0].id == 1
        foundSkills[0].name == "Java"
    }

    def "find by field should throw exception when skill name is null"() {
        when:
        skillService.findByField("name", null)

        then:
        thrown(IllegalArgumentException)
    }

    def "find by field should throw exception when skill name is blank"() {
        when:
        skillService.findByField("name", "")

        then:
        thrown(IllegalArgumentException)
    }

    def "save should return newly saved skill"() {
        given:
        SkillDTO java = new SkillDTO("Java")
        skillDAO.findByField("name", "Java") >> new LinkedHashSet<>()
        skillDAO.save(java) >> Integer.valueOf(67)

        when:
        Integer id = skillService.save(java)

        then:
        id == Integer.valueOf(67)
    }

    def "save should return already saved skill"() {
        given:
        SkillDTO java = new SkillDTO("Java")
        Skill existingJava = new Skill(56, "Java")
        Set<Skill> foundSkills = new LinkedHashSet<>()
        foundSkills.add(existingJava)
        skillDAO.findByField("name", "Java") >> foundSkills

        when:
        Integer id = skillService.save(java)

        then:
        id == Integer.valueOf(56)
    }

    def "save should throw exception when skillDTO is null"() {
        when:
        skillService.save(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "save all should return already saved and newly saved skills"() {
        given:
        SkillDTO java = new SkillDTO("Java")
        SkillDTO python = new SkillDTO("Python")
        Set<SkillDTO> skillDTOSet = new LinkedHashSet<>()
        skillDTOSet.add(java)
        skillDTOSet.add(python)
        Set<Skill> existingPython = new LinkedHashSet<>()
        existingPython.add(new Skill(56, "Python"))
        skillDAO.findByField("name", "Java") >> new LinkedHashSet<Skill>()
        skillDAO.save(java) >> Integer.valueOf(67)
        skillDAO.findByField("name", "Python") >> existingPython

        when:
        Set<Integer> savedIds = skillService.saveAll(skillDTOSet)

        then:
        savedIds[0] == 67
        savedIds[1] == 56
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
}
