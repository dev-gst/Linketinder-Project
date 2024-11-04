package main.ui.entries.candidate


import main.models.entities.Candidate
import main.services.interfaces.AddressService
import main.services.interfaces.SkillService
import main.ui.interfaces.MenuCommand
import main.util.exception.ParamValidation

import java.time.LocalDate
import java.time.ZoneId

class ViewCandidateEntry implements MenuCommand {

    private Candidate candidate

    private final SkillService skillService
    private final AddressService addressService

    ViewCandidateEntry(Candidate candidate, SkillService skillService, AddressService addressService) {
        ParamValidation.requireNonNull(candidate, "Candidate cannot be null")
        ParamValidation.requireNonNull(skillService, "Skill service cannot be null")
        ParamValidation.requireNonNull(addressService, "Address service cannot be null")

        this.candidate = candidate
        this.skillService = skillService
        this.addressService = addressService
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
        println "Skills: ${getCandidateSkills()}"
        println "Address: ${getCandidateAddress()}"
    }

    private String getCandidateSkills() {
        return skillService.getByEntityId(candidate.id, Candidate.class)
    }

    private String getCandidateAddress() {
        return addressService.getByEntityId(candidate.id, Candidate.class)[0]
    }
}
