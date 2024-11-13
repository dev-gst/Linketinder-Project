package application.ui.entries.candidate


import application.models.entities.Candidate
import application.services.interfaces.CandidateService
import application.ui.MenuFactory
import application.ui.helpers.UserInputCollector
import application.ui.interfaces.MenuCommand
import application.ui.interfaces.MenuState
import application.utils.validation.ParamValidation

class LoginCandidateEntry implements MenuCommand {

    private final CandidateService candidateService
    private final MenuFactory menuFactory

    private Candidate candidate

    LoginCandidateEntry(CandidateService candidateService, MenuFactory menuFactory) {
        ParamValidation.requireNonNull(candidateService, "Candidate service cannot be null")
        ParamValidation.requireNonNull(menuFactory, "Menu factory cannot be null")

        this.candidateService = candidateService
        this.menuFactory = menuFactory
    }

    @Override
    void execute() {
        loginCandidate()
    }

    @Override
    String getEntryName() {
        return "Login"
    }

    @Override
    MenuState getNextMenuState(MenuState currentState) {
        if (this.candidate != null) {
            return menuFactory.createCandidateMenu(this.candidate)
        }

        return currentState
    }

    private void loginCandidate() {
        Scanner scanner = new Scanner(System.in)

        println "***** Login de Candidato *****"
        println "Digite o email: "
        String email = UserInputCollector.getString(scanner)

        println "Digite a senha: "
        String password = UserInputCollector.getString(scanner)

        Candidate candidate = candidateService.authenticate(email, password)

        if (candidate != null) {
            println "Login bem-sucedido! Bem-vindo, ${candidate.firstName}!"

            this.candidate = candidate

        } else {
            println "Falha no login. Verifique suas credenciais."
        }
    }
}
