package model.entity

class JobApplicant extends Person {
    static final MAX_SKILLS = 5

    int age
    String CPF
    Skill[] skills

    JobApplicant() {
        this.skills = new Skill[MAX_SKILLS]
    }

    BigInteger getID() {
        return super.ID
    }

    void setID(BigInteger ID) {
       super.ID = ID
    }

    String getName() {
        return super.name
    }

    void setName(String name) {
        super.name = name
    }

    String getDescription() {
        return super.description
    }

    String getEmail() {
        return super.email
    }

    void setEmail(String email) {
        super.email = email
    }

    Address getAddress() {
        return super.address
    }

    void setAddress(Address address) {
        super.address = address
    }

    void addSkill(Skill skill) {
        this.skills << skill
    }
}
