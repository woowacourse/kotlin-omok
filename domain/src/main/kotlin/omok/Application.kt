package omok

import omok.controller.Controller
import omok.view.GameView

fun main() {
    runCatching {
        Controller()
    }.onFailure { exception ->
        GameView().printErrorMessage(exception)
    }
}
