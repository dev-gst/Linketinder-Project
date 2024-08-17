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

    @Override
    String toString() {
        return "Name: $name" +
            "\nDescription: $description" +
            "\nEmail: $email" +
            "\nIdade: $age" +
            "\nAddress: $address" +
            "\nCompetências: $skills\n\n"
    }
}
