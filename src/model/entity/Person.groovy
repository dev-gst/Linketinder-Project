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
            String stringSkill = skill.toString()
            if (stringSkill.contains("_")) {
                stringSkill = stringSkill.replace("_", " ")
            }

            line += stringSkill
            line += ", "
        }

        int lastComma = line.lastIndexOf(", ")
        line = line.substring(0, lastComma)

        return line
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        Person person = (Person) o

        if (ID != person.ID) return false
        if (address != person.address) return false
        if (description != person.description) return false
        if (email != person.email) return false
        if (name != person.name) return false
        if (skills != person.skills) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = (ID != null ? ID.hashCode() : 0)
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (email != null ? email.hashCode() : 0)
        result = 31 * result + (address != null ? address.hashCode() : 0)
        result = 31 * result + (skills != null ? skills.hashCode() : 0)
        return result
    }
}