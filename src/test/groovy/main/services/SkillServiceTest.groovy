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
        thrown(NullPointerException)
    }

    def "get skill by name should return skill"() {
        when:
        skillDAO.getByName("Java") >> new Skill(1, "Java")
        Skill skill = skillService.getSkillByName("Java")

        then:
        skill.id == 1
        skill.name == "Java"
    }

    def "get skill by name should throw exception when skill name is null"() {
        when:
        skillService.getSkillByName(null)

        then:
        thrown(NullPointerException)
    }

    def "get skill by name should throw exception when skill name is blank"() {
        when:
        skillService.getSkillByName("")

        then:
        thrown(IllegalArgumentException)
    }

    def "save should return newly saved skill"() {
        given:
        SkillDTO java = new SkillDTO("Java")

        when:
        skillDAO.getByName("Java") >> null
        skillDAO.save(java) >> 67
        Skill saved = skillService.save(java)

        then:
        saved.id == 67
        saved.name == "Java"
    }

    def "save should return already saved skill"() {
        given:
        SkillDTO java = new SkillDTO("Java")
        Skill existingJava = new Skill(56, "Java")

        when:
        skillDAO.getByName("Java") >> existingJava
        Skill saved = skillService.save(java)

        then:
        saved.id == 56
        saved.name == "Java"
    }

    def "save should throw exception when skillDTO is null"() {
        when:
        skillService.save(null)

        then:
        thrown(NullPointerException)
    }

    def "save all should return saved skills"() {
        given:
        SkillDTO java = new SkillDTO("Java")
        SkillDTO python = new SkillDTO("Python")

        Set<SkillDTO> skillDTOSet = new LinkedHashSet<>()
        skillDTOSet.add(java)
        skillDTOSet.add(python)

        Skill existingPython = new Skill(89, "Python")

        when:
        skillDAO.getByName("Java") >> null
        skillDAO.save(java) >> 67
        skillDAO.getByName("Python") >> existingPython
        Set<Skill> saved = skillService.saveAll(skillDTOSet)

        then:
        saved[0].id == 67
        saved[0].name == "Java"
        saved[1] == existingPython
    }

    def "save all should throw exception when skillDTO set is null"() {
        when:
        skillService.saveAll(null)

        then:
        thrown(NullPointerException)
    }


    def "save all should throw exception when skillDTO set is empty"() {
        when:
        skillService.saveAll(new LinkedHashSet<>())

        then:
        thrown(IllegalArgumentException)
    }
}
