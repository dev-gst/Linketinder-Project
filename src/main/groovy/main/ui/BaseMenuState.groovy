package main.ui

import main.ui.helpers.UserInputCollector
import main.ui.interfaces.MenuCommand
import main.ui.interfaces.MenuState
import main.util.exception.ParamValidation

class BaseMenuState implements MenuState {

    private final int MENU_ENTRIES
    private final Map<Integer, MenuCommand> MENU_COMMANDS
    private MenuState nextState
    final String menuName

    BaseMenuState(Map<Integer, MenuCommand> menuCommands, String menuName) {
        ParamValidation.requireNonNull(menuCommands, "Menu commands cannot be null")
        ParamValidation.requireNonBlank(menuName, "Menu name cannot be blank")

        this.MENU_COMMANDS = menuCommands
        this.menuName = menuName
        this.MENU_ENTRIES = MENU_COMMANDS.size()
    }

    @Override
    void execute() {
        int choice
        do {
            printMenu()
            choice = UserInputCollector.getChoice(MENU_ENTRIES, new Scanner(System.in))
        } while (!selectOption(choice))
    }

    @Override
    MenuState getNextState() {
        return nextState
    }

    @Override
    String getMenuName() {
        return menuName
    }

    private boolean selectOption(int choice) {
        if (MENU_COMMANDS.containsKey(choice)) {
            MenuCommand command = MENU_COMMANDS.get(choice)
            command.execute()
            nextState = command.getNextMenuState(this)

            return true
        }

        println "Opção inválida. Tente novamente."

        return false
    }

    private void printMenu() {
        println()
        println "********** ${menuName} **********\n"
        println gatherMenuLines()
        println "************************************\n"
    }

    private String gatherMenuLines() {
        StringBuilder menuLines = new StringBuilder()
        MENU_COMMANDS.each { entry ->
            menuLines.append("${entry.key} - ${entry.value.getEntryName()}\n")
        }

        return menuLines.toString()
    }


}
