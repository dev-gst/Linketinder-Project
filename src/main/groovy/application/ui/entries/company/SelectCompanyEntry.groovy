package application.ui.entries.company

import application.ui.MenuFactory
import application.ui.interfaces.MenuCommand
import application.ui.interfaces.MenuState
import application.utils.validation.ParamValidation

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
