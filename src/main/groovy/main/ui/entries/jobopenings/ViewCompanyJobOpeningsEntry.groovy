package main.ui.entries.jobopenings

import main.models.entities.Company
import main.models.entities.JobOpening
import main.services.interfaces.JobOpeningService
import main.ui.interfaces.MenuCommand
import main.ui.interfaces.MenuState
import main.util.exception.ParamValidation

class ViewCompanyJobOpeningsEntry implements MenuCommand {

    JobOpeningService jobOpeningService
    Company company

    ViewCompanyJobOpeningsEntry(JobOpeningService jobOpeningService, Company company) {
        ParamValidation.requireNonNull(jobOpeningService, "JobOpeningService cannot be null")
        ParamValidation.requireNonNull(company, "Company cannot be null")

        this.jobOpeningService = jobOpeningService
        this.company = company
    }

    @Override
    void execute() {
        displayCompanyJobOpenings()
    }

    @Override
    String getEntryName() {
        return "Visualizar Vagas da Empresa"
    }

    @Override
    MenuState getNextMenuState(MenuState currentState) {
        return currentState
    }

    private void displayCompanyJobOpenings() {
        Set<JobOpening> jobOpenings = jobOpeningService.getByEntityId(company.id, Company.class)
        println "***** Vagas da Empresa *****"
        jobOpenings.each { jobOpening ->
            println "ID: ${jobOpening.id}"
            println "Nome: ${jobOpening.name}"
            println "Descrição: ${jobOpening.description}"
            println "${jobOpening.isRemote ? 'Remota' : 'Presencial'}"
            println "${jobOpening.isOpen ? 'Aberta' : 'Fechada'}"
            println "-----------------------------"
        }
    }
}
