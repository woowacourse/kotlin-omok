package omok.controller

import omok.domain.OmokGame
import omok.domain.OmokGameListener

class OmokController(
    private val omokGameListener: OmokGameListener,
) {
    fun run() {
        omokGameListener.onOmokStart()
        OmokGame().play(omokGameListener)
    }
}
