package model.entity

class Company extends Person {
    String CNPJ

    @Override
    String toString() {
        return "Name: $name" +
                "\nDescription: $description" +
                "\nEmail: $email" +
                "\nCNPJ: $CNPJ()" +
                "\nAddress: $address" +
                "\nCompetências: ${super.getStringFormatedSkills()}\n"
    }
}
