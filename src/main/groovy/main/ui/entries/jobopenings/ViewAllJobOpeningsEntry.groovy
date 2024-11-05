package main.ui.entries.jobopenings

import main.models.entities.Address
import main.models.entities.JobOpening
import main.services.interfaces.AddressService
import main.services.interfaces.JobOpeningService
import main.ui.interfaces.MenuCommand
import main.ui.interfaces.MenuState
import main.util.exception.ParamValidation

class ViewAllJobOpeningsEntry implements MenuCommand {

    JobOpeningService jobOpeningService
    AddressService addressService

    ViewAllJobOpeningsEntry(JobOpeningService jobOpeningService, AddressService addressService) {
        ParamValidation.requireNonNull(jobOpeningService, "JobOpeningService cannot be null")
        ParamValidation.requireNonNull(addressService, "AddressService cannot be null")

        this.jobOpeningService = jobOpeningService
        this.addressService = addressService
    }

    @Override
    void execute() {
        checkForJobOpenings()
    }

    @Override
    String getEntryName() {
        return "Visualizar vagas"
    }

    @Override
    MenuState getNextMenuState(MenuState currentState) {
        return currentState
    }

    private checkForJobOpenings() {
        Set<JobOpening> jobOpenings = jobOpeningService.getAll()
        if (jobOpenings.isEmpty()) {
            println "Nenhuma vaga disponível no momento."
        } else {
            viewJobOpenings(jobOpenings)
        }
    }

    private void viewJobOpenings(Set<JobOpening> jobOpenings) {
        println "***** Vagas Disponíveis *****"
        jobOpenings.each { jobOpening ->
            println "Nome: ${jobOpening.name}"
            println "Descrição: ${jobOpening.description}"
            println "${jobOpening.isRemote ? 'Remota' : 'Presencial'}"
            println "${jobOpening.isOpen ? 'Aberta' : 'Fechada'}"
            println "Endereço: ${getJobOpeningAddress(jobOpening)}"
            println()
        }
    }

    private String getJobOpeningAddress(JobOpening jobOpening) {
        Set<Address> addresses = addressService.getByEntityId(jobOpening.id, JobOpening.class)
        if (addresses.isEmpty()) {
            return "Endereço não disponível"
        }

        Address address = addresses.first()
        return "${address.city}, ${address.region} - ${address.country}"
    }
}
