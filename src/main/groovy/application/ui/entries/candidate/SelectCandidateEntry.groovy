package application.ui.entries.candidate

import application.ui.MenuFactory
import application.ui.interfaces.MenuCommand
import application.ui.interfaces.MenuState
import application.utils.validation.ParamValidation

class SelectCandidateEntry implements MenuCommand {

    MenuFactory menuFactory

    SelectCandidateEntry(MenuFactory menuFactory) {
        ParamValidation.requireNonNull(menuFactory, "MenuFactory cannot be null")

        this.menuFactory = menuFactory
    }

    @Override
    void execute() {}

    @Override
    String getEntryName() {
        return "Sou um candidato"
    }

    @Override
    MenuState getNextMenuState(MenuState ignored) {
        return menuFactory.createCandidateAuthMenu()
    }
}
