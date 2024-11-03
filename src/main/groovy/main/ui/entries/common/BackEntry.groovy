package main.ui.entries.common

import main.ui.interfaces.MenuCommand

class BackEntry implements MenuCommand {

    @Override
    void execute() {
        println "Voltando..."
    }

    @Override
    String getEntryName() {
        return "Voltar"
    }
}
