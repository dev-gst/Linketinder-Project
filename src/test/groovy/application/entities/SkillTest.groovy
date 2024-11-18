package application.entities

import application.models.entities.Skill
import spock.lang.Specification

class SkillTest extends Specification {

    def "skill Builder should create a skill"() {
        when:
        Skill skill = new Skill.Builder()
                .id(1)
                .name("Java")
                .build()

        then:
        skill.id == 1
        skill.name == "Java"
    }

    def "skill Builder should throw exception when id is negative"() {
        when:
        new Skill.Builder()
                .id(-1)
                .name("Java")
                .build()

        then:
        thrown(IllegalArgumentException)
    }

    def "skill Builder should throw exception when name is null"() {
        when:
        new Skill.Builder()
                .id(1)
                .name(null)
                .build()

        then:
        thrown(IllegalArgumentException)
    }

    def "skill Builder should throw exception when name is blank"() {
        when:
        new Skill.Builder()
                .id(1)
                .name("")
                .build()

        then:
        thrown(IllegalArgumentException)
    }

    def "skill equals should return true when skills are equal"() {
        when:
        Skill skill1 = new Skill.Builder()
                .id(1)
                .name("Java")
                .build()

        Skill skill2 = new Skill.Builder()
                .id(1)
                .name("Java")
                .build()

        then:
        skill1 == skill2
    }

    def "skill equals should return false when skills are not equal"() {
        when:
        Skill skill1 = new Skill.Builder()
                .id(1)
                .name("Java")
                .build()

        Skill skill2 = new Skill.Builder()
                .id(2)
                .name("Java")
                .build()

        then:
        skill1 != skill2
    }

    def "hash code should return same value when skills are equal"() {
        when:
        Skill skill1 = new Skill.Builder()
                .id(1)
                .name("Java")
                .build()

        Skill skill2 = new Skill.Builder()
                .id(1)
                .name("Java")
                .build()

        then:
        skill1.hashCode() == skill2.hashCode()
    }

    def "hash code should return different value when skills are not equal"() {
        when:
        Skill skill1 = new Skill.Builder()
                .id(1)
                .name("Java")
                .build()

        Skill skill2 = new Skill.Builder()
                .id(2)
                .name("Java")
                .build()

        then:
        skill1.hashCode() != skill2.hashCode()
    }
}
