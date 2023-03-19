package omok

import omok.controller.Controller
import omok.domain.OmokGame
import omok.view.GameView

fun main() {
    runCatching {
        Controller(GameView(), OmokGame()).gameStart()
    }.onFailure { exception ->
        GameView().printErrorMessage(exception)
    }
}
