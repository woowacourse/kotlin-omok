package omok.view

import omok.domain.board.BoardStatus
import omok.domain.stone.LatestStone
import omok.domain.stone.StoneColor

interface GameView {
    fun readStoneWithLatestStone(
        stoneColor: StoneColor,
        lastStone: LatestStone,
    ): String

    fun printStartMessage()

    fun printBoard(
        board: List<List<BoardStatus>>,
        stone: StoneColor,
    )

    fun printErrorMessage(message: String?)

    fun printWinner(color: StoneColor)

    fun readStone(
        stone: StoneColor,
        latestStone: LatestStone,
    ): String
}
