package omok

import omok.controller.Controller
import omok.view.GameView

fun main() {
    runCatching {
        Controller(GameView(), OmokGame()).gameStart()
    }.onFailure { exception ->
        GameView().printErrorMessage(exception)
    }
}
