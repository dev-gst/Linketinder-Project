package application.ui.interfaces

interface MenuState {

    void execute()

    MenuState getNextState()

    String getMenuName()
}