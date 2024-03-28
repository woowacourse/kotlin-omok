package omok.view.output

import omok.model.Board
import omok.model.entity.StoneColor

interface OutputView {
    fun printStartGuide()

    fun printTurn(
        board: Board,
        color: StoneColor,
    )

    fun printWinner(
        board: Board,
        color: StoneColor,
    )

    fun printError(message: String)
}
