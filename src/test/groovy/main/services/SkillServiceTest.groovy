package main.services

import main.models.dtos.anonresponse.AnonSkillDTO
import main.models.dtos.request.skill.SkillDTO
import main.models.entities.Candidate
import main.models.entities.jobOpening.JobOpening
import main.models.entities.skill.Skill
import main.repositories.SkillDAO
import main.util.exception.custom.ClassNotFoundException
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

    def "get by id should return skill when found"() {
        Skill java = new Skill(1, "Java")
        skillDAO.getById(1) >> java

        when:
        Skill foundSkill = skillService.getById(1)

        then:
        foundSkill.id == 1
        foundSkill.name == "Java"
        1 * skillDAO.getById(1) >> java
    }

    def "get by id should throw exception when skill id is less than 1"() {
        when:
        skillService.getById(0)

        then:
        thrown(IllegalArgumentException)
    }

    def "get anon by id should return anon skill when found"() {
        Skill java = new Skill(1, "Java")
        skillDAO.getById(1) >> java

        when:
        AnonSkillDTO anonSkillDTO = skillService.getAnonById(1)

        then:
        anonSkillDTO.name == "Java"
        1 * skillDAO.getById(1) >> java
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
        skills.add(new Skill(1, "Java"))
        skillDAO.getByCandidateId(1) >> skills

        when:
        Set<Skill> foundSkills = skillService.getByEntityId(1, Candidate.class)

        then:
        foundSkills.size() == 1
        foundSkills[0].id == 1
        foundSkills[0].name == "Java"
    }

    def "get by entity id should return skills for job opening"() {
        given:
        Set<Skill> skills = new LinkedHashSet<>()
        skills.add(new Skill(2, "Python"))
        skillDAO.getByJobOpeningId(1) >> skills

        when:
        Set<Skill> foundSkills = skillService.getByEntityId(1, JobOpening.class)

        then:
        foundSkills.size() == 1
        foundSkills[0].id == 2
        foundSkills[0].name == "Python"
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
        skills.add(new Skill(1, "Java"))
        skillDAO.getByField("name", "Java") >> skills

        when:
        Set<Skill> foundSkills = skillService.getByField("name", "Java")

        then:
        foundSkills[0].id == 1
        foundSkills[0].name == "Java"
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
        skills.add(new Skill(1, "Java"))
        skills.add(new Skill(2, "Python"))
        skillDAO.getAll() >> skills

        when:
        Set<Skill> foundSkills = skillService.getAll()

        then:
        foundSkills.size() == 2
        foundSkills[0].id == 1
        foundSkills[0].name == "Java"
        foundSkills[1].id == 2
        foundSkills[1].name == "Python"
    }

    def "save should return newly saved skill"() {
        given:
        SkillDTO java = new SkillDTO("Java")
        skillDAO.getByField("name", "Java") >> new LinkedHashSet<>()
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
        skillDAO.getByField("name", "Java") >> foundSkills

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
        skillDAO.getByField("name", "Java") >> new LinkedHashSet<Skill>()
        skillDAO.save(java) >> Integer.valueOf(67)
        skillDAO.getByField("name", "Python") >> existingPython

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
