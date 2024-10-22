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
        skillDAO.save(java) >> 67

        when:
        Skill saved = skillService.save(java)

        then:
        saved.id == 67
        saved.name == "Java"
    }

    def "save should return already saved skill"() {
        given:
        SkillDTO java = new SkillDTO("Java")
        Skill existingJava = new Skill(56, "Java")
        Set<Skill> foundSkills = new LinkedHashSet<>()
        foundSkills.add(existingJava)

        when:
        skillDAO.findByField("name", "Java") >> foundSkills
        Skill saved = skillService.save(java)

        then:
        saved.id == 56
        saved.name == "Java"
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

        when:
        skillDAO.findByField("name", "Java") >> new LinkedHashSet<Skill>()
        skillDAO.save(java) >> 67
        skillDAO.findByField("name", "Python") >> existingPython

        Set<Skill> saved = skillService.saveAll(skillDTOSet)

        then:
        saved[0].id == 67
        saved[0].name == "Java"
        saved[1] == existingPython[0]
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
