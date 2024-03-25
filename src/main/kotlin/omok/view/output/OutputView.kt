package omok.view.output

import omok.model.board.Board
import omok.model.entity.StoneColor

interface OutputView {
    fun printStartGuide()

    fun printTurn(
        board: Board,
        stoneColor: StoneColor,
    )

    fun printWinner(
        board: Board,
        stoneColor: StoneColor,
    )

    fun printInAppropriatePlace(message: String)
}
