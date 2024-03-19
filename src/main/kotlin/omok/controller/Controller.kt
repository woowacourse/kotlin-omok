package omok.controller

import omok.model.Position
import omok.view.InputView

class Controller {
    fun play() {
        val position = readPosition()
    }

    private fun readPosition(): Position {
        return InputView.readPosition()
    }
}
