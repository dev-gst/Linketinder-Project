package model.entity

class Company extends Person {
    String CNPJ

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false
        if (!super.equals(o)) return false

        Company company = (Company) o

        if (CNPJ != company.CNPJ) return false

        return true
    }

    @Override
    int hashCode() {
        int result = super.hashCode()
        result = 31 * result + (CNPJ != null ? CNPJ.hashCode() : 0)
        return result
    }

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
