package application.controllers

import application.controllers.interfaces.MenuController
import application.ui.interfaces.MenuState
import application.utils.validation.ParamValidation

class DefaultMenuController implements MenuController {

    private Stack<MenuState> stateStack

    DefaultMenuController(MenuState initialState) {
        ParamValidation.requireNonNull(initialState, "Initial state cannot be null")

        stateStack = new Stack<>()
        stateStack.push(initialState)
    }

    void run() {
        while (!stateStack.isEmpty()) {
            MenuState currentState = stateStack.peek()
            currentState.execute()

            MenuState nextState = currentState.getNextState()
            if (nextState == null) {
                stateStack.pop()
            } else if (nextState != currentState) {
                stateStack.push(nextState)
            }
        }
    }
}
