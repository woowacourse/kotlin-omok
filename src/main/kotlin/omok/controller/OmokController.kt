package omok.controller

import omok.model.OmokGame
import omok.model.board.BoardSize
import omok.view.OmokInputView
import omok.view.OmokOutputView

class OmokController(
    private val inputView: OmokInputView,
    private val outputView: OmokOutputView,
) {
    fun play() {
        val size = BoardSize.OMOK_BOARD_SIZE
        OmokGame(inputView, outputView).play(BoardSize(size))
    }
}
