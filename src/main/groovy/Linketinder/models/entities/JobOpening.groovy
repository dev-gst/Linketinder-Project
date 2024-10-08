package Linketinder.models.entities

class JobOpening {
    int id
    String name
    String description
    boolean isRemote
    boolean isOpen
    Company company
    Address address
    List<Skill> requiredSkills = new ArrayList<Skill>()

    void addSkill(Skill skill) {
        requiredSkills.add(skill)
    }

    void removeSkill(Skill skill) {
        requiredSkills.remove(skill)
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        JobOpening that = (JobOpening) o

        if (id != that.id) return false
        if (isOpen != that.isOpen) return false
        if (isRemote != that.isRemote) return false
        if (address != that.address) return false
        if (company != that.company) return false
        if (description != that.description) return false
        if (name != that.name) return false
        if (requiredSkills != that.requiredSkills) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = id
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (isRemote ? 1 : 0)
        result = 31 * result + (isOpen ? 1 : 0)
        result = 31 * result + (company != null ? company.hashCode() : 0)
        result = 31 * result + (address != null ? address.hashCode() : 0)
        result = 31 * result + (requiredSkills != null ? requiredSkills.hashCode() : 0)

        return result
    }

    @Override
    String toString() {
        return "Detalhes da Vaga:\n" +
                "ID: ${id}\n" +
                "Nome: ${name}\n" +
                "Descrição: ${description}\n" +
                "Remoto: ${isRemote ? 'Sim' : 'Não'}\n" +
                "Aberta: ${isOpen ? 'Sim' : 'Não'}\n" +
                "Empresa: ${company}\n" +
                "Endereço:\n${address}\n" +
                "Habilidades Requeridas: ${requiredSkills}"
    }
}
