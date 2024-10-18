package main.models.entities

import main.util.config.Env

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class Candidate {
    int id
    String firstName
    String lastName
    String email
    String password
    String description
    Instant birthDate
    String cpf
    String education
    Address address
    Set<Skill> skills = new HashSet<Skill>()

    void addSkill (Skill skill) {
        this.skills.add(skill)
    }

    void removeSkill (Skill skill) {
        this.skills.remove(skill)
    }

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        Candidate candidate = (Candidate) o

        if (id != candidate.id) return false
        if (address != candidate.address) return false
        if (birthDate != candidate.birthDate) return false
        if (cpf != candidate.cpf) return false
        if (description != candidate.description) return false
        if (education != candidate.education) return false
        if (email != candidate.email) return false
        if (firstName != candidate.firstName) return false
        if (lastName != candidate.lastName) return false
        if (password != candidate.password) return false
        if (skills != candidate.skills) return false

        return true
    }

    @Override
    int hashCode() {
        int result
        result = id
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0)
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0)
        result = 31 * result + (email != null ? email.hashCode() : 0)
        result = 31 * result + (password != null ? password.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0)
        result = 31 * result + (cpf != null ? cpf.hashCode() : 0)
        result = 31 * result + (education != null ? education.hashCode() : 0)
        result = 31 * result + (address != null ? address.hashCode() : 0)
        result = 31 * result + (skills != null ? skills.hashCode() : 0)

        return result
    }

    @Override
    String toString() {
        return "Candidato:\n" +
                "ID: ${id}\n" +
                "Nome: ${firstName}\n" +
                "Sobrenome: ${lastName}\n" +
                "Email: ${email}\n" +
                "Senha: ${password}\n" +
                "Descrição: ${description}\n" +
                "Data de Nascimento: ${LocalDate.ofInstant(this.birthDate, Env.TIMEZONE as ZoneId).toString()}\n" +
                "CPF: ${cpf}\n" +
                "Educação: ${education}\n" +
                "Endereço: \n${address}\n" +
                "Habilidades: ${skills}"
    }
}
