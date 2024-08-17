package model.entity

abstract class Person {
    BigInteger ID
    String name
    String description
    String email
    Address address
    Set<Skill> skills

    Person() {
        this.skills = new LinkedHashSet()
    }

    void addSkill(Skill... skills) {
        for (Skill skill in skills) {
            this.skills.add(skill)
        }
    }

    String getStringFormatedSkills() {
        String line = ""
        for (Skill skill in this.skills) {
            line += skill.toString() + ", "
        }

        int lastComma = line.lastIndexOf(", ")
        line = line.substring(0, lastComma)

        return line
    }
}