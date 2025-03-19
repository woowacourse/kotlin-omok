package omok.controller

import omok.model.OmokGame
import omok.view.OmokInputView
import omok.view.OmokOutputView

class OmokController(
    private val inputView: OmokInputView,
    private val outputView: OmokOutputView,
) {
    fun play() {
        OmokGame(inputView, outputView).play()
    }
}
