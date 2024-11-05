package main.ui.entries.common

import main.ui.interfaces.MenuCommand
import main.ui.interfaces.MenuState

class ExitEntry implements MenuCommand {

    @Override
    void execute() {
        println("Saindo do sistema...")
    }

    @Override
    String getEntryName() {
        return "Sair"
    }

    @Override
    MenuState getNextMenuState(MenuState currentState) {
        return null
    }
}
