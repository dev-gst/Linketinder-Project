package model.entity

class JobApplicant extends Person {
    int age
    String CPF

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false
        if (!super.equals(o)) return false

        JobApplicant that = (JobApplicant) o

        if (age != that.age) return false
        if (CPF != that.CPF) return false

        return true
    }

    @Override
    int hashCode() {
        int result = super.hashCode()
        result = 31 * result + age
        result = 31 * result + (CPF != null ? CPF.hashCode() : 0)
        return result
    }

    @Override
    String toString() {
        return "Name: $name" +
            "\nDescription: $description" +
            "\nEmail: $email" +
            "\nIdade: $age" +
            "\nAddress: $address" +
            "\nCompetências: ${super.getStringFormatedSkills()}\n"
    }
}
