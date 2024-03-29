package woowacourse.omok.view.output

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.entity.StoneColor

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
