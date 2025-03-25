package omok.view

import omok.domain.board.BoardStatus
import omok.domain.stone.LatestStone
import omok.domain.stone.StoneColor

class ConsoleGameView(
    private val outputView: OutputView,
    private val inputView: InputView,
) : GameView {
    override fun readStoneWithLatestStone(
        stoneColor: StoneColor,
        lastStone: LatestStone,
    ): String {
        return inputView.readStoneWithLatestStone(stoneColor, lastStone)
    }

    override fun printStartMessage() {
        outputView.printStartMessage()
    }

    override fun printBoard(
        board: List<List<BoardStatus>>,
        stone: StoneColor,
    ) {
        outputView.printBoard(board, stone)
    }

    override fun printErrorMessage(message: String?) {
        outputView.printErrorMessage(message)
    }

    override fun printWinner(color: StoneColor) {
        outputView.printPrintWinner(color)
    }

    override fun readStone(
        stone: StoneColor,
        latestStone: LatestStone,
    ): String {
        return inputView.readStoneWithLatestStone(stone, latestStone)
    }
}
