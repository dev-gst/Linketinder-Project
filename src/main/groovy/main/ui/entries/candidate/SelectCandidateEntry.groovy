package main.ui.entries.candidate

import main.ui.MenuFactory
import main.ui.interfaces.MenuCommand
import main.ui.interfaces.MenuState
import main.util.exception.ParamValidation

class SelectCandidateEntry implements MenuCommand {

    MenuFactory menuFactory

    SelectCandidateEntry(MenuFactory menuFactory) {
        ParamValidation.requireNonNull(menuFactory, "MenuFactory cannot be null")

        this.menuFactory = menuFactory
    }

    @Override
    void execute() {
        println "Sou um candidato"
    }

    @Override
    String getEntryName() {
        return "Sou um candidato"
    }

    @Override
    MenuState getNextMenuState(MenuState ignored) {
        return menuFactory.createCandidateAuthMenu()
    }
}
