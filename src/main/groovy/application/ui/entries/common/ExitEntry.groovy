package application.ui.entries.common

import application.ui.interfaces.MenuCommand
import application.ui.interfaces.MenuState

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
