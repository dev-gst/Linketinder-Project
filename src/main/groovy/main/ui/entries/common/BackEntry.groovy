package main.ui.entries.common

import main.ui.interfaces.MenuCommand
import main.ui.interfaces.MenuState

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
