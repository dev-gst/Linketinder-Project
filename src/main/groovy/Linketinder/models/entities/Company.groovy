package Linketinder.models.entities

class Company {
    int id
    String name
    String email
    String password
    String description
    String cnpj
    Address address
    List<JobOpening> jobOpenings = new ArrayList<JobOpening>()

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        Company company = (Company) o

        if (id != company.id) return false
        if (name != company.name) return false
        if (email != company.email) return false
        if (password != company.password) return false
        if (description != company.description) return false
        if (cnpj != company.cnpj) return false
        if (address != company.address) return false
        if (jobOpenings != company.jobOpenings) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = id
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (email != null ? email.hashCode() : 0)
        result = 31 * result + (password != null ? password.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (cnpj != null ? cnpj.hashCode() : 0)
        result = 31 * result + (address != null ? address.hashCode() : 0)
        result = 31 * result + (jobOpenings != null ? jobOpenings.hashCode() : 0)

        return result
    }

    String toString() {
        return "Empresa:\n" +
                "ID: ${id}\n" +
                "Nome: ${name}\n" +
                "Email: ${email}\n" +
                "Senha: ${password}\n" +
                "Descrição: ${description}\n" +
                "CNPJ: ${cnpj}\n" +
                "Endereço:\n${address}\n" +
                "Vagas de Emprego: ${jobOpenings}"
    }
}
