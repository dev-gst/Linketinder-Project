package main.ui.entries.company

import main.ui.MenuFactory
import main.ui.interfaces.MenuCommand
import main.ui.interfaces.MenuState
import main.util.exception.ParamValidation

class SelectCompanyEntry implements MenuCommand {

    private final MenuFactory menuFactory

    SelectCompanyEntry(MenuFactory menuFactory) {
        ParamValidation.requireNonNull(menuFactory, "MenuFactory cannot be null")

        this.menuFactory = menuFactory
    }

    @Override
    void execute() {}

    @Override
    String getEntryName() {
        return "Selecionar Empresa"
    }

    @Override
    MenuState getNextMenuState(MenuState ignored) {
        return menuFactory.createCandidateAuthMenu()
    }
}
