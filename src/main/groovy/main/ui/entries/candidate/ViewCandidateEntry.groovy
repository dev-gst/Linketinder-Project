package main.ui.entries.candidate

import main.models.entities.Address
import main.models.entities.Candidate
import main.models.entities.Skill
import main.ui.interfaces.MenuCommand
import main.util.exception.ParamValidation

import java.time.LocalDate
import java.time.ZoneId

class ViewCandidateEntry implements MenuCommand {

    private Candidate candidate
    private Set<Skill> skills
    private Address address

    ViewCandidateEntry(Candidate candidate, Set<Skill> skills, Address address) {
        ParamValidation.requireNonNull(candidate, "Candidate cannot be null")
        ParamValidation.requireNonNull(skills, "Skills cannot be null")
        ParamValidation.requireNonNull(address, "Address cannot be null")

        this.candidate = candidate
        this.skills = skills
        this.address = address
    }

    @Override
    void execute() {
        displayCandidateProfile()
    }

    @Override
    String getEntryName() {
        return "Visualizar perfil"
    }

    private void displayCandidateProfile() {
        System.out.println("Candidate profile")
        println "Name: ${candidate.firstName} ${candidate.lastName}"
        println "Email: ${candidate.loginDetails.email}"
        println "Description: ${candidate.description}"
        println "Birth date: ${LocalDate.ofInstant(candidate.birthDate, ZoneId.systemDefault())}"
        println "CPF: ${candidate.cpf}"
        println "Education: ${candidate.education}"
        println "Skills: ${skills}"
        println "Address: ${address}"
    }
}
