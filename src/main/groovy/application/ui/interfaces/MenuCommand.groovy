package application.ui.interfaces

interface MenuCommand {

    void execute()

    String getEntryName()

    MenuState getNextMenuState(MenuState currentState)
}