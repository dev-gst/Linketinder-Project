package main.entities.skill

import main.models.entities.Skill
import spock.lang.Specification

class SkillTest extends Specification {

    def "skill constructor should throw exception when id is negative"() {
        when:
        new Skill(-1, "Java")

        then:
        thrown(IllegalArgumentException)
    }

    def "skill constructor should throw exception when name is null"() {
        when:
        new Skill(1, null)

        then:
        thrown(IllegalArgumentException)
    }

    def "skill constructor should throw exception when name is blank"() {
        when:
        new Skill(1, "")

        then:
        thrown(IllegalArgumentException)
    }

    def "skill equals should return true when skills are equal"() {
        when:
        Skill skill1 = new Skill(1, "Java")
        Skill skill2 = new Skill(1, "Java")

        then:
        skill1 == skill2
    }

    def "skill equals should return false when skills are not equal"() {
        when:
        Skill skill1 = new Skill(1, "Java")
        Skill skill2 = new Skill(2, "Java")

        then:
        skill1 != skill2
    }

    def "hash code should return same value when skills are equal"() {
        when:
        Skill skill1 = new Skill(1, "Java")
        Skill skill2 = new Skill(1, "Java")

        then:
        skill1.hashCode() == skill2.hashCode()
    }

    def "hash code should return different value when skills are not equal"() {
        when:
        Skill skill1 = new Skill(1, "Java")
        Skill skill2 = new Skill(2, "Java")

        then:
        skill1.hashCode() != skill2.hashCode()
    }
}
