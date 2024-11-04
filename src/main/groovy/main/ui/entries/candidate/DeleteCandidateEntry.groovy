package main.ui.entries.candidate

import main.models.entities.Candidate
import main.services.interfaces.CandidateService
import main.ui.helpers.UserInputCollector
import main.ui.interfaces.MenuCommand
import main.util.exception.ParamValidation

class DeleteCandidateEntry implements MenuCommand {

    CandidateService candidateService

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

    private void deleteCandidateProfile() {
        println "Tem certeza que deseja deletar sua conta? (s/N)"
        boolean confirmation = UserInputCollector.getBoolean(new Scanner(System.in))

        if (confirmation) {
            candidateService.deleteById(candidate.id)
            println "Conta deletada com sucesso."
        }

        println "Ação cancelada."
    }
}
