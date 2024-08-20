package Linketinder.models.entities

class Candidate extends Person {
    int age
    String CPF

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false
        if (!super.equals(o)) return false

        Candidate that = (Candidate) o

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
            "\nAge: $age" +
            "\nAddress: $address" +
            "\nSkills: ${super.getStringFormatedSkills()}\n"
    }
}
