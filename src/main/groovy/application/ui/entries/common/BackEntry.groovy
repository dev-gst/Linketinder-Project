package application.ui.entries.common

import application.ui.interfaces.MenuCommand
import application.ui.interfaces.MenuState

class BackEntry implements MenuCommand {

    @Override
    void execute() {
        println "Voltando..."
    }

    @Override
    String getEntryName() {
        return "Voltar"
    }

    @Override
    MenuState getNextMenuState(MenuState ignored) {
        return null
    }
}
