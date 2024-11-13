package application.ui.entries.candidate

import application.models.entities.Candidate
import application.services.interfaces.CandidateService
import application.ui.helpers.UserInputCollector
import application.ui.interfaces.MenuCommand
import application.ui.interfaces.MenuState
import application.utils.validation.ParamValidation

class DeleteCandidateEntry implements MenuCommand {

    CandidateService candidateService
    boolean confirmation

    Candidate candidate

    DeleteCandidateEntry(
            Candidate candidate,
            CandidateService candidateService
    ) {
        ParamValidation.requireNonNull(candidate, "Candidate cannot be null")
        ParamValidation.requireNonNull(candidateService, "CandidateService cannot be null")

        this.candidate = candidate
        this.candidateService = candidateService
    }

    @Override
    void execute() {
        deleteCandidateProfile()
    }

    @Override
    String getEntryName() {
        return "Deletar conta"
    }

    @Override
    MenuState getNextMenuState(MenuState currentState) {
        if (confirmation) {
            return null
        }

        return currentState
    }

    private void deleteCandidateProfile() {
        println "Tem certeza que deseja deletar sua conta? (s/N)"
        confirmation = UserInputCollector.getBoolean(new Scanner(System.in))

        if (confirmation) {
            candidateService.deleteById(candidate.id)
            println "Conta deletada com sucesso."
        }

        println "Ação cancelada."
    }
}
