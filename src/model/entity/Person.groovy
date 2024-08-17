package model.entity

abstract class Person {
    private static final int MAX_SKILLS = 5

    BigInteger ID
    String name
    String description
    String email
    Address address
    Skill[] skills

    Person() {
        this.skills = new Skill[MAX_SKILLS]
    }

    void addSkill(Skill... skills) {
        int marker = 0
        for (Skill skill in skills) {
            for (int i = marker; i < this.skills.length; i++) {
                if (this.skills[this.skills.length - 1]) {
                    throw new IllegalStateException("Container is full (can only contain 5 elements for now)")
                }

                if (!this.skills[i]) {
                    this.skills[i] = skill
                    marker = i
                    break
                }
            }
        }
    }

    String getStringFormatedSkills() {
        String line = ""
        for (Skill skill in skills) {
            if (skill)
                line += skill.toString() + ", "
        }

        int lastComma = line.lastIndexOf(", ")
        line = line.substring(0, lastComma)

        return line
    }
}