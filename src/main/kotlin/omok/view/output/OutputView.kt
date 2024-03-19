package omok.view.output

import omok.model.Board
import omok.model.entity.StoneColor

interface OutputView {
    fun printStartGuide()

    fun printOneTurn(
        board: Board,
        color: StoneColor,
    )
}
