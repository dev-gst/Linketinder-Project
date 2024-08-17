package model.entity

class JobApplicant extends Person {
    int age
    String CPF

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
