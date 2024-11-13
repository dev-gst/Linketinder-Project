package application.ui.entries.jobopenings

import application.models.entities.Company
import application.models.entities.JobOpening
import application.services.interfaces.JobOpeningService
import application.ui.interfaces.MenuCommand
import application.ui.interfaces.MenuState
import application.utils.validation.ParamValidation

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
